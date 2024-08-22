package com.plyushkin.income.controller.request;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.IncomeCategoryNumber;
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
