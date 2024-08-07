package com.plyushkin.budget.investment.swap;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
class CurrencySwapDealNumberAttributeConverter implements AttributeConverter<CurrencySwapDealNumber, Long> {
    @Override
    public Long convertToDatabaseColumn(CurrencySwapDealNumber attribute) {
        return attribute.getValue();
    }

    @Override
    public CurrencySwapDealNumber convertToEntityAttribute(Long dbData) {
        return new CurrencySwapDealNumber(dbData);
    }
}
