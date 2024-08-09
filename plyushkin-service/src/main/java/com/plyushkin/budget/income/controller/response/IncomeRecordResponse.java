package com.plyushkin.budget.income.controller.response;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import com.plyushkin.budget.income.IncomeNumber;
import com.plyushkin.budget.income.IncomeRecord;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
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
