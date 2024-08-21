package com.plyushkin.budget.usecase.command;

import com.plyushkin.budget.domain.AbstractNumber;
import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record UpdateRecordCommand<RN extends AbstractNumber<RN>, CN extends AbstractNumber<CN>>(
        WalletId walletId,
        RN number,
        @Nullable
        LocalDate date,
        @Nullable
        Currency currency,
        @Nullable
        Money amount,
        @Nullable
        CN categoryNumber,
        String comment
) {
}
