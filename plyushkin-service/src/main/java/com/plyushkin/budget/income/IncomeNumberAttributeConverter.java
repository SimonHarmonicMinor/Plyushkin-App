package com.plyushkin.budget.income;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class IncomeNumberAttributeConverter implements AttributeConverter<IncomeNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(IncomeNumber attribute) {
        return attribute.getValue();
    }

    @Override
    public IncomeNumber convertToEntityAttribute(Long dbData) {
        return new IncomeNumber(dbData);
    }
}
