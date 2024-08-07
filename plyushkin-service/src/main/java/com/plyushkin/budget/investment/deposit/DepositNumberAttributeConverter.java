package com.plyushkin.budget.investment.deposit;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class DepositNumberAttributeConverter implements AttributeConverter<DepositNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(DepositNumber attribute) {
        return attribute.getValue();
    }

    @Override
    public DepositNumber convertToEntityAttribute(Long dbData) {
        return new DepositNumber(dbData);
    }
}
