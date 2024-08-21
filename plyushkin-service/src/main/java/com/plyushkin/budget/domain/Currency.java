package com.plyushkin.budget.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(implementation = CurrencyEnum.class, description = "Currency")
public interface Currency {

    String value();

    static Currency of(String value) {
        try {
            return CurrencyEnum.of(value);
        } catch (IllegalArgumentException e) {
            return new StringCurrency(value);
        }
    }
}

@Schema(enumAsRef = true)
enum CurrencyEnum implements Currency {
    RUB, DOLLAR, EURO;

    public static CurrencyEnum of(String value) {
        for (CurrencyEnum currencyEnum : values()) {
            if (currencyEnum.name().equals(value)) {
                return currencyEnum;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

    @Override
    public String value() {
        return name();
    }
}

record StringCurrency(String value) implements Currency {

}
