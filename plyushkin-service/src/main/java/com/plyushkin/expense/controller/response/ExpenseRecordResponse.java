package com.plyushkin.expense.controller.response;

import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.expense.ExpenseCategoryNumber;
import com.plyushkin.expense.ExpenseNumber;
import com.plyushkin.expense.domain.ExpenseRecord;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ExpenseRecordResponse(
        @NotNull
        WalletId walletId,
        @NotNull
        ExpenseNumber number,
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
