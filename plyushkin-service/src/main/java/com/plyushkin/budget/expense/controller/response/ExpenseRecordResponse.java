package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.ExpenseRecord;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseRecordResponse(
        @NotNull
        WalletId walletId,
        @NotNull
        UserId whoDid,
        @NotNull
        LocalDate date,
        @NotNull
        Currency currency,
        @NotNull
        Money amount,
        @NotNull
        ExpenseCategoryNumber categoryNumber,
        @NotNull
        String comment
) {
    public ExpenseRecordResponse(ExpenseRecord entity) {
        this(
                entity.getWalletId(),
                entity.getWhoDid(),
                entity.getDate(),
                entity.getCurrency(),
                entity.getAmount(),
                entity.getCategory().getNumber(),
                entity.getComment()
        );
    }
}
