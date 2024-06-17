package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.controller.request.CreateCategoryRequest;
import com.plyushkin.budget.expense.controller.response.CreateCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse.ErrorNonUniqueNameResponse;
import com.plyushkin.budget.expense.controller.response.InvalidWalletIdResponse;
import com.plyushkin.budget.expense.usecase.ExpenseNoteCategoryUseCase;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException.NonUniqueNamePerWalletId;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import com.plyushkin.wallet.WalletId.InvalidWalletIdException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseNoteCategoryController {

  @PostMapping("/wallets/{walletId}/expenseNotes")
  @Operation(responses = {
      @ApiResponse(
          responseCode = "201",
          content = @Content(schema = @Schema(implementation = CreateCategoryResponse.class))
      ),
      @ApiResponse(
          responseCode = "400",
          content = {
              @Content(
                  schema = @Schema(
                      oneOf = {
                          ErrorCreateCategoryResponse.class,
                          InvalidWalletIdResponse.class,
                      }
                  )
              )
          }
      ),
  })
  @SneakyThrows
  public ResponseEntity<CreateCategoryResponse> createCategory(
      @NotNull @PathVariable String walletId,
      @Valid @RequestBody CreateCategoryRequest request
  ) {
    ExpenseNoteCategoryNumber number = useCase.createCategory(new CreateCategoryCommand(
        request.name(),
        WalletId.create(walletId),
        UserId.createRandom()
    ));
    return ResponseEntity.created(
            URI.create("/api/wallets/%s/expenseNotes/%s".formatted(walletId, number.getValue()))
        )
        .body(new CreateCategoryResponse(number.getValue()));
  }

  private final ExpenseNoteCategoryUseCase useCase;

  @ExceptionHandler(InvalidWalletIdException.class)
  public ResponseEntity<InvalidWalletIdResponse> handleInvalidWalletIdException(
      InvalidWalletIdException e
  ) {
    log.warn("Handled InvalidWalletIdResponse", e);
    return ResponseEntity.status(400)
        .body(new InvalidWalletIdResponse(e.getPassedValue()));
  }

  @ExceptionHandler(CreateCategoryException.class)
  public ResponseEntity<ErrorCreateCategoryResponse> handleCreateCategoryException(
      CreateCategoryException e
  ) {
    log.warn("Handled ErrorCreateCategoryResponse", e);
    return switch (e) {
      case NonUniqueNamePerWalletId err -> ResponseEntity.status(400)
          .body(new ErrorNonUniqueNameResponse(err.getName()));
      default -> throw new IllegalStateException("Unexpected value: " + e, e);
    };
  }
}
