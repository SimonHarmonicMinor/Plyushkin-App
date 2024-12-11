package com.plyushkin.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record ID<T>(@Column(name = "id") UUID value) {
    public static <T> ID<T> random() {
        return new ID<>(UUID.randomUUID());
    }
}
