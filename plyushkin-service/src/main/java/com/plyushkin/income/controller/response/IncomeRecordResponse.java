package com.plyushkin.income.controller.response;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.IncomeCategoryNumber;
import com.plyushkin.shared.IncomeNumber;
import com.plyushkin.income.domain.IncomeRecord;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record IncomeRecordResponse(
        @NotNull
        WalletId walletId,
        @NotNull
        IncomeNumber number,
        @NotNull
        UserId whoDid,
        @NotNull
        LocalDate date,
        @NotNull
        Currency currency,
        @NotNull
        Money amount,
        @NotNull
        IncomeCategoryNumber categoryNumber,
        @NotNull
        String comment
) {
    public IncomeRecordResponse(IncomeRecord entity) {
        this(
                entity.getWalletId(),
                entity.getNumber(),
                entity.getWhoDid(),
                entity.getDate(),
                entity.getCurrency(),
                entity.getAmount(),
                entity.getCategory().getNumber(),
                entity.getComment()
        );
    }
}
