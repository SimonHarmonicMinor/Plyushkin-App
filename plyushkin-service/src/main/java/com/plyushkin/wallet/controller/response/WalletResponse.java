package com.plyushkin.wallet.controller.response;

import com.plyushkin.shared.UserId;
import com.plyushkin.wallet.domain.Wallet;
import com.plyushkin.shared.WalletId;
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
