package com.plyushkin.expense.controller.response;

import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.expense.ExpenseCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record ExpenseCategoryResponse(
        @NotNull
        String name,
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
                category.getName(),
                category.getNumber(),
                category.getWalletId(),
                category.getWhoCreated(),
                Optional.ofNullable(category.getParent())
                        .map(ExpenseCategory::getNumber)
                        .orElse(null)
        );
    }
}
