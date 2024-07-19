package com.plyushkin.wallet;

public interface CurrencyType {

    String value();

    static CurrencyType of(String value) {
        try {
            return CurrencyTypeEnum.of(value);
        } catch (IllegalArgumentException e) {
            return new StringCurrencyType(value);
        }
    }
}

enum CurrencyTypeEnum implements CurrencyType {
    RUB, DOLLAR, EURO;

    public static CurrencyTypeEnum of(String value) {
        for (CurrencyTypeEnum currencyEnum : values()) {
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

record StringCurrencyType(String value) implements CurrencyType {

}
