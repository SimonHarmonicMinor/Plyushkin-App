package com.plyushkin.budget.expense.controller.request;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ExpenseRecordCreateRequest(
        @NotNull
        LocalDate date,
        @NotNull
        Currency currency,
        @NotNull
        Money amount,
        @NotNull
        ExpenseCategoryNumber categoryNumber,
        @NotNull
        @Size(min = 0, max = 200)
        String comment
) {
}
