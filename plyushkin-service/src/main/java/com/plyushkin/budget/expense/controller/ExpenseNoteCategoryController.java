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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/budget/expense")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ExpenseNoteCategoryController {

  private final ExpenseNoteCategoryUseCase useCase;

  @PostMapping("/note")
  public CreateCategoryResponse createCategory(@Valid @RequestBody CreateCategoryRequest request)
      throws InvalidWalletIdException, CreateCategoryException {
    ExpenseNoteCategoryNumber number = useCase.createCategory(new CreateCategoryCommand(
        request.name(),
        WalletId.create(request.walletId()),
        UserId.createRandom()
    ));
    return new CreateCategoryResponse(number.getValue());
  }

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
