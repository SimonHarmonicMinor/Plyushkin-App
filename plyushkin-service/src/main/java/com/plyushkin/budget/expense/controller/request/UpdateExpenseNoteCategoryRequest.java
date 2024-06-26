package com.plyushkin.budget.expense.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public record UpdateExpenseNoteCategoryRequest(@NotNull String name, @Nullable Long newParentNumber) {
}
