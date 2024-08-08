package com.plyushkin.budget.base.usecase.command;

import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record ListRecordsCommand(
        WalletId walletId,
        int pageNumber,
        int pageSize,
        @Nullable LocalDate from,
        @Nullable LocalDate to
) {
}
