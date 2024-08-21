package com.plyushkin.infra;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;

import static java.util.Objects.requireNonNullElse;

public record DefaultErrorResponse(
        @NotNull
        String message,
        @NotNull
        Instant timestamp
) {
    public DefaultErrorResponse(Throwable ex) {
        this(ex.getMessage(), Instant.now());
    }

    public DefaultErrorResponse(String message,
                                Instant timestamp) {
        final var trim = requireNonNullElse(message, "").trim();
        this.message = trim.substring(0, Math.min(trim.length(), 200));
        this.timestamp = timestamp;
    }
}
