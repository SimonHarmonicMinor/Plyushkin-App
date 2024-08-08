package com.plyushkin.budget.base.usecase.command;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.base.AbstractNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;

import java.time.LocalDate;

public record CreateRecordCommand<CN extends AbstractNumber<CN>>(
        WalletId walletId,
        UserId whoCreated,
        LocalDate date,
        Currency currency,
        Money amount,
        CN categoryNumber,
        String comment
) {
}
