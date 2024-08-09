package com.plyushkin.budget.income.controller.request;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record IncomeRecordUpdateRequest(
        @Nullable
        LocalDate date,
        @Nullable
        Currency currency,
        @Nullable
        Money amount,
        @Nullable
        IncomeCategoryNumber categoryNumber,
        @Nullable
        @Size(min = 0, max = 200)
        String comment
) {
}
