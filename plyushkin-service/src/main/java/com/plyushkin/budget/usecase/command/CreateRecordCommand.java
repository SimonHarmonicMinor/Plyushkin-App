package com.plyushkin.budget.usecase.command;

import com.plyushkin.budget.domain.AbstractNumber;
import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
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
