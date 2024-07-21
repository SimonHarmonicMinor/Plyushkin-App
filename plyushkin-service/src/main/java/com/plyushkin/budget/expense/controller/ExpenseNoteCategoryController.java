package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.expense.ExpenseNoteCategoryEntityGraph;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.controller.request.CreateExpenseNoteCategoryRequest;
import com.plyushkin.budget.expense.controller.request.UpdateExpenseNoteCategoryRequest;
import com.plyushkin.budget.expense.controller.response.CreateExpenseNoteCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse.ErrorNonUniqueNameResponse;
import com.plyushkin.budget.expense.controller.response.ExpenseNoteCategoryResponse;
import com.plyushkin.budget.expense.controller.response.UpdateExpenseNoteCategoryResponse;
import com.plyushkin.budget.expense.repository.ExpenseNoteCategoryRepository;
import com.plyushkin.budget.expense.usecase.ExpenseNoteCategoryUseCase;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.UpdateCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException.NonUniqueNamePerWalletId;
import com.plyushkin.budget.expense.usecase.exception.UpdateExpenseNoteCategoryException;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
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
                                    }
                            )
                    )
            )
    })
    public ResponseEntity<CreateExpenseNoteCategoryResponse> createCategory(@NotNull @PathVariable WalletId walletId,
                                                                            @Valid @RequestBody CreateExpenseNoteCategoryRequest request)
            throws CreateCategoryException {
        ExpenseNoteCategoryNumber number = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                walletId,
                UserId.createRandom()
        ));
        return ResponseEntity.created(
                        URI.create("/api/wallets/%s/expenseNoteCategories/%s".formatted(walletId, number.getValue()))
                )
                .body(new CreateExpenseNoteCategoryResponse(number));
    }

    @PutMapping("/wallets/{walletId}/expenseNoteCategories/{number}")
    public void updateCategory(@NotNull @PathVariable ExpenseNoteCategoryNumber number,
                               @NotNull @PathVariable WalletId walletId,
                               @NotNull @Valid @RequestBody UpdateExpenseNoteCategoryRequest request)
            throws UpdateExpenseNoteCategoryException {
        useCase.update(
                new UpdateCommand(
                        walletId,
                        number,
                        request.name(),
                        request.newParentNumber()
                )
        );
    }

    @GetMapping("/wallets/{walletId}/expenseNoteCategories/{number}")
    public ResponseEntity<ExpenseNoteCategoryResponse> getCategory(@NotNull @PathVariable ExpenseNoteCategoryNumber number,
                                                                   @NotNull @PathVariable WalletId walletId) {
        return repository.findByWalletIdAndNumber(
                        walletId,
                        number,
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
    public List<ExpenseNoteCategoryResponse> listCategories(@NotNull @PathVariable WalletId walletId) {
        return repository.findAllByWalletId(
                        walletId,
                        ExpenseNoteCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).stream()
                .map(ExpenseNoteCategoryResponse::new)
                .toList();
    }

    @ExceptionHandler(UpdateExpenseNoteCategoryException.class)
    public ResponseEntity<UpdateExpenseNoteCategoryResponse> handleUpdateExpenseNoteCategoryException(UpdateExpenseNoteCategoryException e) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new UpdateExpenseNoteCategoryResponse(e.getMessage()));
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
