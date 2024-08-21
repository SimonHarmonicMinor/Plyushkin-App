package com.plyushkin.expense.domain;

import com.plyushkin.expense.ExpenseCategoryNumber;
import jakarta.persistence.AttributeConverter;
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
