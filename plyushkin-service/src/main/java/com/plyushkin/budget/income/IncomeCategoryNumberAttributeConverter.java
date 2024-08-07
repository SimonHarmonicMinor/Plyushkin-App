package com.plyushkin.budget.income;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
class IncomeCategoryNumberAttributeConverter implements AttributeConverter<IncomeCategoryNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(IncomeCategoryNumber attribute) {
        return attribute.getValue();
    }

    @Override
    @SneakyThrows
    public IncomeCategoryNumber convertToEntityAttribute(Long dbData) {
        return IncomeCategoryNumber.create(dbData);
    }
}
