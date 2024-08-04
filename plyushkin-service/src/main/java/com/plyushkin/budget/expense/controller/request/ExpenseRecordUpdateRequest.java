package com.plyushkin.budget.expense.controller.request;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ExpenseRecordUpdateRequest(
        @Nullable
        LocalDate date,
        @Nullable
        Currency currency,
        @Nullable
        Money amount,
        @Nullable
        ExpenseCategoryNumber categoryNumber,
        @Nullable
        @Size(min = 0, max = 200)
        String comment
) {
}
