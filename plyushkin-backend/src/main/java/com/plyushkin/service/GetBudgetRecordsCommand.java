package com.plyushkin.service;

import com.plyushkin.domain.budget.Wallet;
import com.plyushkin.domain.value.ID;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

public record GetBudgetRecordsCommand(
        ID<Wallet> walletId,
        @Nullable LocalDate from,
        @Nullable LocalDate to
) {
}
