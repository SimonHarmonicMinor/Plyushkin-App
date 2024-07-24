package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record ExpenseCategoryResponse(
        @NotNull
        ExpenseNoteCategoryNumber number,
        @NotNull
        WalletId walletId,
        @NotNull
        UserId whoCreatedId,
        @Nullable
        ExpenseNoteCategoryNumber parentNumber
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
