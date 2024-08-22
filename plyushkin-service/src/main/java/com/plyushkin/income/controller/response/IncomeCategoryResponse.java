package com.plyushkin.income.controller.response;

import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.shared.IncomeCategoryNumber;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
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
