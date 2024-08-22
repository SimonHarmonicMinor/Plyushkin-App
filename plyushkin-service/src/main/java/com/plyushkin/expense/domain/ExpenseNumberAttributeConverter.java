package com.plyushkin.expense.domain;

import com.plyushkin.shared.ExpenseNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
class ExpenseNumberAttributeConverter implements AttributeConverter<ExpenseNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(ExpenseNumber attribute) {
        return attribute.getValue();
    }

    @Override
    @SneakyThrows
    public ExpenseNumber convertToEntityAttribute(Long dbData) {
        return ExpenseNumber.create(dbData);
    }
}
