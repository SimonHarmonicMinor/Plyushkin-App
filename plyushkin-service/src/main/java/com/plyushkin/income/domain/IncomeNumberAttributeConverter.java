package com.plyushkin.income.domain;

import com.plyushkin.income.IncomeNumber;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;

@Converter
class IncomeNumberAttributeConverter implements AttributeConverter<IncomeNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(IncomeNumber attribute) {
        return attribute.getValue();
    }

    @Override
    @SneakyThrows
    public IncomeNumber convertToEntityAttribute(Long dbData) {
        return IncomeNumber.create(dbData);
    }
}
