package com.plyushkin.budget.expense;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

class ExpenseCategoryNumberAttributeConverter implements AttributeConverter<ExpenseCategoryNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(ExpenseCategoryNumber number) {
        return number.getValue();
    }

    @Override
    @SneakyThrows
    public ExpenseCategoryNumber convertToEntityAttribute(Long aLong) {
        return ExpenseCategoryNumber.create(aLong);
    }
}
