package com.plyushkin.infra.web;

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
        this.message = requireNonNullElse(message, "").trim().substring(0, 200);
        this.timestamp = timestamp;
    }
}
