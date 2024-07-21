package com.plyushkin.budget.expense.controller.request;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record UpdateExpenseNoteCategoryRequest(@NotNull String name, @Nullable ExpenseNoteCategoryNumber newParentNumber) {
}
