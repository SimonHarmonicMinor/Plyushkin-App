package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.expense.ExpenseNoteCategoryEntityGraph;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.ExpenseNoteNumber.InvalidExpenseNoteIdException;
import com.plyushkin.budget.expense.controller.request.CreateExpenseNoteCategoryRequest;
import com.plyushkin.budget.expense.controller.response.*;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse.ErrorNonUniqueNameResponse;
import com.plyushkin.budget.expense.repository.ExpenseNoteCategoryRepository;
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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseNoteCategoryController {

    private final ExpenseNoteCategoryUseCase useCase;
    private final ExpenseNoteCategoryRepository repository;

    @PostMapping("/wallets/{walletId}/expenseNoteCategories")
    @Operation(responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = CreateExpenseNoteCategoryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            schema = @Schema(
                                    oneOf = {
                                            ErrorCreateCategoryResponse.class,
                                            InvalidWalletIdResponse.class
                                    }
                            )
                    )
            )
    })
    public ResponseEntity<CreateExpenseNoteCategoryResponse> createCategory(@NotNull @PathVariable String walletId,
                                                                            @Valid @RequestBody CreateExpenseNoteCategoryRequest request)
            throws InvalidWalletIdException, CreateCategoryException {
        ExpenseNoteCategoryNumber number = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                WalletId.create(walletId),
                UserId.createRandom()
        ));
        return ResponseEntity.created(
                        URI.create("/api/wallets/%s/expenseNoteCategories/%s".formatted(walletId, number.getValue()))
                )
                .body(new CreateExpenseNoteCategoryResponse(number.getValue()));
    }

    @GetMapping("/wallets/{walletId}/expenseNoteCategories/{number}")
    public ResponseEntity<ExpenseNoteCategoryResponse> getCategory(@NotNull @PathVariable @Min(1) long number,
                                                                   @NotNull @PathVariable String walletId)
            throws InvalidWalletIdException, ExpenseNoteCategoryNumber.InvalidExpenseNoteCategoryNumberException {
        return repository.findByWalletIdAndNumber(
                        WalletId.create(walletId),
                        ExpenseNoteCategoryNumber.create(number),
                        ExpenseNoteCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).map(ExpenseNoteCategoryResponse::new)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/wallets/{walletId}/expenseNoteCategories")
    public List<ExpenseNoteCategoryResponse> listCategories(@NotNull @PathVariable String walletId)
            throws InvalidWalletIdException {
        return repository.findAllByWalletId(
                        WalletId.create(walletId),
                        ExpenseNoteCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).stream()
                .map(ExpenseNoteCategoryResponse::new)
                .toList();
    }

    @ExceptionHandler(InvalidExpenseNoteIdException.class)
    public ResponseEntity<InvalidExpenseNoteCategoryNumberResponse> handleInvalidExpenseNoteIdException(InvalidExpenseNoteIdException e) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new InvalidExpenseNoteCategoryNumberResponse(e.getWrongValue()));
    }

    @ExceptionHandler(InvalidWalletIdException.class)
    public ResponseEntity<InvalidWalletIdResponse> handleInvalidWalletIdException(InvalidWalletIdException e) {
        log.warn("Handled InvalidWalletIdResponse", e);
        return ResponseEntity.status(400)
                .body(new InvalidWalletIdResponse(e.getPassedValue()));
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<ErrorCreateCategoryResponse> handleCreateCategoryException(CreateCategoryException e) {
        log.warn("Handled ErrorCreateCategoryResponse", e);
        return switch (e) {
            case NonUniqueNamePerWalletId err -> ResponseEntity.status(400)
                    .body(new ErrorNonUniqueNameResponse(err.getName()));
        };
    }

    private static void log4xx(Throwable e) {
        log.warn("Handled 4xx", e);
    }
}
