package com.plyushkin.budget.base.usecase.command;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;

public record CreateCategoryCommand(
        String name, WalletId walletId, UserId whoCreated
) {
}
