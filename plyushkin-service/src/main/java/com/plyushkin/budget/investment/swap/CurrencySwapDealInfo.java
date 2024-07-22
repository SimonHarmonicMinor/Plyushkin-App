package com.plyushkin.budget.investment.swap;

import com.plyushkin.budget.Money;
import com.plyushkin.budget.Currency;
import com.plyushkin.budget.CurrencyAttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public record CurrencySwapDealInfo(@Convert(converter = CurrencyAttributeConverter.class) Currency currency,
                                   Money amount) {
}
