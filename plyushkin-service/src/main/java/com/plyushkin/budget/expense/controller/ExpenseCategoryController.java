package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.base.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.base.usecase.command.UpdateCategoryCommand;
import com.plyushkin.budget.base.usecase.exception.CategoryNotFoundException;
import com.plyushkin.budget.base.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.base.usecase.exception.DeleteCategoryException;
import com.plyushkin.budget.base.usecase.exception.UpdateCategoryUseCaseException;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.controller.request.ExpenseCategoryCreateRequest;
import com.plyushkin.budget.expense.controller.request.ExpenseCategoryUpdateRequest;
import com.plyushkin.budget.expense.controller.response.ExpenseCategoryResponse;
import com.plyushkin.budget.expense.usecase.ExpenseCategoryUseCase;
import com.plyushkin.user.CurrentUserIdProvider;
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
    private final CurrentUserIdProvider currentUserIdProvider;

    @PostMapping("/wallets/{walletId}/expenseCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ResponseEntity<ExpenseCategoryResponse> createExpenseCategory(
            @NotNull @PathVariable WalletId walletId,
            @NotNull @Valid @RequestBody ExpenseCategoryCreateRequest request
    )
            throws CreateCategoryException {
        ExpenseCategory category = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                walletId,
                currentUserIdProvider.get()
        ));
        return ResponseEntity.created(
                        URI.create(
                                "/api/wallets/%s/expenseCategories/%s"
                                        .formatted(
                                                walletId,
                                                category.getNumber().getValue()
                                        ))
                )
                .body(new ExpenseCategoryResponse(category));
    }

    @PatchMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ExpenseCategoryResponse updateExpenseCategory(
            @NotNull @PathVariable ExpenseCategoryNumber number,
            @NotNull @PathVariable WalletId walletId,
            @NotNull @Valid @RequestBody ExpenseCategoryUpdateRequest request
    )
            throws UpdateCategoryUseCaseException {
        return new ExpenseCategoryResponse(
                useCase.updateCategory(
                        walletId,
                        number,
                        new UpdateCategoryCommand<>(
                                request.name(),
                                request.newParentNumber()
                        )
                )
        );
    }

    @GetMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public ExpenseCategoryResponse getExpenseCategory(
            @NotNull @PathVariable ExpenseCategoryNumber number,
            @NotNull @PathVariable WalletId walletId
    ) throws CategoryNotFoundException {
        return new ExpenseCategoryResponse(useCase.getCategory(walletId, number));
    }

    @GetMapping("/wallets/{walletId}/expenseCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public List<ExpenseCategoryResponse> listExpenseCategories(@NotNull @PathVariable WalletId walletId) {
        return useCase.listCategories(walletId)
                .stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @DeleteMapping("/wallets/{walletId}/expenseCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteExpenseCategory(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseCategoryNumber number
    ) throws DeleteCategoryException {
        useCase.deleteCategory(walletId, number);
    }
}
