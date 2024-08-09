package com.plyushkin.budget.income.controller.request;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record IncomeRecordCreateRequest(
        @NotNull
        LocalDate date,
        @NotNull
        Currency currency,
        @NotNull
        Money amount,
        @NotNull
        IncomeCategoryNumber categoryNumber,
        @NotNull
        @Size(min = 0, max = 200)
        String comment
) {
}
