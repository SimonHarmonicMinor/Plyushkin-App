package com.plyushkin.domain.budget;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
class MoneyConverter implements AttributeConverter<Money, BigDecimal> {
    @Override
    public BigDecimal convertToDatabaseColumn(Money attribute) {
        return attribute.value();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbData) {
        return new Money(dbData);
    }
}
