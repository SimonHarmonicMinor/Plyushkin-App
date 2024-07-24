package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import jakarta.validation.constraints.NotNull;

public record CreateExpenseNoteCategoryResponse(@NotNull ExpenseCategoryNumber number) {

}
