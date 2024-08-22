package com.plyushkin.expense.controller.response;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.ExpenseCategoryNumber;
import com.plyushkin.shared.ExpenseNumber;
import com.plyushkin.expense.domain.ExpenseRecord;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
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
