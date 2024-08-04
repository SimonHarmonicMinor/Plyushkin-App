package com.plyushkin.infra.web;

import com.fasterxml.jackson.core.JsonParser;
import lombok.SneakyThrows;

abstract class DoubleSerdeProvider<T> extends NumberSerdeProvider<T, Double> {
    @Override
    public Double parseNumber(String text) {
        return Double.parseDouble(text);
    }

    @Override
    @SneakyThrows
    public Double getNumberValue(JsonParser p) {
        return p.getDoubleValue();
    }
}
