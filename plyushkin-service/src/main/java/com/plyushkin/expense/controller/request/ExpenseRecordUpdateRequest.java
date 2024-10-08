package com.plyushkin.expense.controller.request;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.ExpenseCategoryNumber;
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
