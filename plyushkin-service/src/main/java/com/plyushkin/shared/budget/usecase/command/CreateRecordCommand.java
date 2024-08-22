package com.plyushkin.shared.budget.usecase.command;

import com.plyushkin.shared.budget.domain.AbstractNumber;
import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;

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
