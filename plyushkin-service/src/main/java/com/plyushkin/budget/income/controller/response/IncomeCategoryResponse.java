package com.plyushkin.budget.income.controller.response;

import com.plyushkin.budget.income.IncomeCategory;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record IncomeCategoryResponse(
        @NotNull
        String name,
        @NotNull
        IncomeCategoryNumber number,
        @NotNull
        WalletId walletId,
        @NotNull
        UserId whoCreatedId,
        @Nullable
        IncomeCategoryNumber parentNumber
) {
    public IncomeCategoryResponse(IncomeCategory category) {
        this(
                category.getName(),
                category.getNumber(),
                category.getWalletId(),
                category.getWhoCreated(),
                Optional.ofNullable(category.getParent())
                        .map(IncomeCategory::getNumber)
                        .orElse(null)
        );
    }
}
