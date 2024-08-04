package com.plyushkin.budget.expense;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class ExpenseNumberAttributeConverter implements AttributeConverter<ExpenseNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(ExpenseNumber attribute) {
        return attribute.getValue();
    }

    @Override
    public ExpenseNumber convertToEntityAttribute(Long dbData) {
        return new ExpenseNumber(dbData);
    }
}
