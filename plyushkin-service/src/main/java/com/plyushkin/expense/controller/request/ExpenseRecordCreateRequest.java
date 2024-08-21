package com.plyushkin.expense.controller.request;

import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.expense.ExpenseCategoryNumber;
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
