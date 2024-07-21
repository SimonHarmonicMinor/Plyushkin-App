package com.plyushkin.budget.investment.swap;

import com.plyushkin.budget.AbstractNumber;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CurrencySwapDealNumber extends AbstractNumber {
    protected CurrencySwapDealNumber(long value) throws InvalidNumberException {
        super(value);
    }

    public static CurrencySwapDealNumber create(long value) throws InvalidCurrencySwapNumberException {
        try {
            return new CurrencySwapDealNumber(value);
        } catch (InvalidNumberException e) {
            throw new InvalidCurrencySwapNumberException(e.getMessage(), e);
        }
    }

    @StandardException
    public static class InvalidCurrencySwapNumberException extends InvalidNumberException {
    }
}
