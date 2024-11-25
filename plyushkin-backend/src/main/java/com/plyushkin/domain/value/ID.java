package com.plyushkin.domain.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record ID<T>(@Column(name = "id") long value) {
    public static <T> ID<T> now() {
        return new ID<>(System.currentTimeMillis());
    }
}
