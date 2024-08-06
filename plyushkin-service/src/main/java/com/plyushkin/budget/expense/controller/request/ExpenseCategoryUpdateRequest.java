package com.plyushkin.budget.expense.controller.request;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import jakarta.annotation.Nullable;

public record ExpenseCategoryUpdateRequest(
        @Nullable String name,
        @Nullable ExpenseCategoryNumber newParentNumber
) {
}
