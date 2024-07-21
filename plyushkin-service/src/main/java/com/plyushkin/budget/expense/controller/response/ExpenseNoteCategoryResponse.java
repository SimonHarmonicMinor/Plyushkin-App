package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record ExpenseNoteCategoryResponse(
        @NotNull
        ExpenseNoteCategoryNumber number,
        @NotNull
        WalletId walletId,
        @NotNull
        UserId whoCreatedId,
        @Nullable
        ExpenseNoteCategoryNumber parentNumber
) {
    public ExpenseNoteCategoryResponse(ExpenseNoteCategory category) {
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
