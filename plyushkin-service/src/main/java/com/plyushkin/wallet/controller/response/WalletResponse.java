package com.plyushkin.wallet.controller.response;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.Wallet;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.constraints.NotNull;

public record WalletResponse(
        @NotNull
        WalletId id,
        @NotNull
        String name,
        @NotNull
        UserId createdBy
) {
    public WalletResponse(Wallet wallet) {
        this(wallet.getId(), wallet.getName(), wallet.getCreatedBy());
    }
}
