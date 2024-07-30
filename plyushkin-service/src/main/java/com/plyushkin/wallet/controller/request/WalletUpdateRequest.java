package com.plyushkin.wallet.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Size;

public record WalletUpdateRequest(
        @Nullable
        @Size(min = 1, max = 200, message = "Name must be within boundaries: [{min}, {max}]")
        String name
) {
}
