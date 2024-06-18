package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public record ExpenseNoteCategoryResponse(
        @Schema
        long number,
        @NotNull
        String walletId,
        @NotNull
        String whoCreatedId,
        @Nullable
        Long parentNumber
) {
    public ExpenseNoteCategoryResponse(ExpenseNoteCategory category) {
        this(
                category.getNumber().getValue(),
                category.getWalletId().getValue(),
                category.getWhoCreated().getValue(),
                Optional.ofNullable(category.getParent())
                        .map(AbstractCategory::getNumber)
                        .map(ExpenseNoteCategoryNumber::getValue)
                        .orElse(null)
        );
    }
}
