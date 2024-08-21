package com.plyushkin.infra;

import com.fasterxml.jackson.core.JsonParser;
import lombok.SneakyThrows;

public abstract class LongSerdeProvider<T> extends NumberSerdeProvider<T, Long> {
    @Override
    public Long parseNumber(String text) {
        return Long.parseLong(text);
    }

    @Override
    @SneakyThrows
    public Long getNumberValue(JsonParser p) {
        return p.getLongValue();
    }
}
