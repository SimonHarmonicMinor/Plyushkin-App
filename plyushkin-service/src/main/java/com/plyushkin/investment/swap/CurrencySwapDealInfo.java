package com.plyushkin.investment.swap;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.CurrencyAttributeConverter;
import com.plyushkin.shared.budget.domain.Money;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

@Embeddable
public record CurrencySwapDealInfo(@Convert(converter = CurrencyAttributeConverter.class) Currency currency,
                                   Money amount) {
}
