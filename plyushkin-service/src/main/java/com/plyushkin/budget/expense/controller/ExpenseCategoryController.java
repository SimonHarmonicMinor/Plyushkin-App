package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryEntityGraph;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.controller.request.ExpenseNoteCategoryCreateRequest;
import com.plyushkin.budget.expense.controller.request.ExpenseNoteCategoryUpdateRequest;
import com.plyushkin.budget.expense.controller.response.ExpenseCategoryResponse;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.usecase.ExpenseCategoryUseCase;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.UpdateCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException.NonUniqueNamePerWalletId;
import com.plyushkin.budget.expense.usecase.exception.DeleteCategoryException;
import com.plyushkin.budget.expense.usecase.exception.UpdateExpenseCategoryException;
import com.plyushkin.infra.web.DefaultErrorResponse;
import com.plyushkin.user.service.CurrentUserIdProvider;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseCategoryController {

    private final ExpenseCategoryUseCase useCase;
    private final ExpenseCategoryRepository repository;
    private final CurrentUserIdProvider currentUserIdProvider;

    @PostMapping("/wallets/{walletId}/expenseCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ResponseEntity<ExpenseCategoryResponse> createCategory(
            @NotNull @PathVariable WalletId walletId,
            @NotNull @Valid @RequestBody ExpenseNoteCategoryCreateRequest request
    )
            throws CreateCategoryException {
        ExpenseCategory category = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                walletId,
                currentUserIdProvider.get()
        ));
        return ResponseEntity.created(
                        URI.create(
                                "/api/wallets/%s/expenseNoteCategories/%s"
                                        .formatted(
                                                walletId,
                                                category.getNumber().getValue()
                                        ))
                )
                .body(new ExpenseCategoryResponse(category));
    }

    @PatchMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ExpenseCategoryResponse updateCategory(@NotNull @PathVariable ExpenseCategoryNumber number,
                                                  @NotNull @PathVariable WalletId walletId,
                                                  @NotNull @Valid @RequestBody ExpenseNoteCategoryUpdateRequest request)
            throws UpdateExpenseCategoryException {
        return new ExpenseCategoryResponse(
                useCase.updateCategory(
                        walletId,
                        number,
                        new UpdateCommand(
                                request.name(),
                                request.newParentNumber()
                        )
                )
        );
    }

    @GetMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public ResponseEntity<ExpenseCategoryResponse> getCategory(
            @NotNull @PathVariable ExpenseCategoryNumber number,
            @NotNull @PathVariable WalletId walletId
    ) {
        return repository.findByWalletIdAndNumber(
                        walletId,
                        number,
                        ExpenseCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).map(ExpenseCategoryResponse::new)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/wallets/{walletId}/expenseCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public List<ExpenseCategoryResponse> listCategories(@NotNull @PathVariable WalletId walletId) {
        return repository.findAllByWalletId(
                        walletId,
                        ExpenseCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @DeleteMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteCategory(@PathVariable @NotNull WalletId walletId,
                               @PathVariable @NotNull ExpenseCategoryNumber number) throws DeleteCategoryException {
        useCase.deleteCategory(walletId, number);
    }

    @ExceptionHandler(UpdateExpenseCategoryException.class)
    public ResponseEntity<DefaultErrorResponse> handleUpdateExpenseNoteCategoryException(
            UpdateExpenseCategoryException e
    ) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new DefaultErrorResponse(e));
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<DefaultErrorResponse> handleCreateCategoryException(CreateCategoryException e) {
        log4xx(e);
        return switch (e) {
            case NonUniqueNamePerWalletId err -> ResponseEntity.status(400)
                    .body(new DefaultErrorResponse(err));
        };
    }

    @ExceptionHandler(DeleteCategoryException.class)
    public ResponseEntity<DefaultErrorResponse> handleDeleteCategoryException(DeleteCategoryException e) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new DefaultErrorResponse(e));
    }

    private static void log4xx(Throwable e) {
        log.warn("Handled 4xx", e);
    }
}
