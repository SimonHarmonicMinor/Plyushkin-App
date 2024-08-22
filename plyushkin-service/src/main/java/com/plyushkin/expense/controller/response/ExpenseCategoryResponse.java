package com.plyushkin.expense.controller.response;

import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.shared.ExpenseCategoryNumber;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
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
