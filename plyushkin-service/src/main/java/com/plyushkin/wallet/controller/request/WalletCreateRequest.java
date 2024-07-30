package com.plyushkin.wallet.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record WalletCreateRequest(
        @NotNull(message = "Name cannot be null")
        @Size(min = 1, max = 200, message = "Name must be within boundaries: [{min}, {max}]")
        String name
) {
}
