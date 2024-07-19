package com.plyushkin.wallet;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class CurrencyTypeAttributeConverter implements AttributeConverter<CurrencyType, String> {

    @Override
    public String convertToDatabaseColumn(CurrencyType attribute) {
        return attribute.value();
    }

    @Override
    public CurrencyType convertToEntityAttribute(String dbData) {
        return CurrencyType.of(dbData);
    }
}
