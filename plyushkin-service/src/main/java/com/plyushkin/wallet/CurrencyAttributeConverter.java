package com.plyushkin.wallet;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CurrencyAttributeConverter implements AttributeConverter<Currency, String> {

    @Override
    public String convertToDatabaseColumn(Currency attribute) {
        return attribute.value();
    }

    @Override
    public Currency convertToEntityAttribute(String dbData) {
        return Currency.of(dbData);
    }
}
