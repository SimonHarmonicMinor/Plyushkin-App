package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import jakarta.validation.constraints.NotNull;

public record CreateExpenseNoteCategoryResponse(@NotNull ExpenseNoteCategoryNumber number) {

}
