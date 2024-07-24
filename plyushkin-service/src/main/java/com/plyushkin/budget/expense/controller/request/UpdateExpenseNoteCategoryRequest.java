package com.plyushkin.budget.expense.controller.request;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record UpdateExpenseNoteCategoryRequest(
        @NotNull String name,
        @Nullable ExpenseCategoryNumber newParentNumber
) {
}
