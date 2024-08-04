package com.plyushkin.infra.web;

import com.fasterxml.jackson.core.JsonParser;
import lombok.SneakyThrows;

abstract class LongSerdeProvider<T> extends NumberSerdeProvider<T, Long> {
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
