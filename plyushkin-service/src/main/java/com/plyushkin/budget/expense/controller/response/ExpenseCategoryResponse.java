package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record ExpenseCategoryResponse(
        @NotNull
        ExpenseCategoryNumber number,
        @NotNull
        WalletId walletId,
        @NotNull
        UserId whoCreatedId,
        @Nullable
        ExpenseCategoryNumber parentNumber
) {
    public ExpenseCategoryResponse(ExpenseCategory category) {
        this(
                category.getNumber(),
                category.getWalletId(),
                category.getWhoCreated(),
                Optional.ofNullable(category.getParent())
                        .map(AbstractCategory::getNumber)
                        .orElse(null)
        );
    }
}
