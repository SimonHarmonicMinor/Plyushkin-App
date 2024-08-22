package com.plyushkin.shared.budget.usecase.command;

import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;

public record CreateCategoryCommand(
        String name, WalletId walletId, UserId whoCreated
) {
}
