package com.plyushkin.budget.usecase.command;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;

public record CreateCategoryCommand(
        String name, WalletId walletId, UserId whoCreated
) {
}
