package com.plyushkin.budget.investment.swap;

import com.plyushkin.budget.base.AbstractNumber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.StandardException;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
@Schema(implementation = Long.class, description = "CurrencySwapDealNumber", minimum = "1")
public class CurrencySwapDealNumber implements AbstractNumber<CurrencySwapDealNumber> {
    private final long value;

    public static CurrencySwapDealNumber create(long value) throws InvalidCurrencySwapNumberException {
        if (value <= 0) {
            throw new InvalidCurrencySwapNumberException(
                    "Value should be positive but it is: " + value
            );
        }
        return new CurrencySwapDealNumber(value);
    }

    @Override
    public CurrencySwapDealNumber increment() {
        return new CurrencySwapDealNumber(this.value + 1);
    }

    @StandardException
    public static class InvalidCurrencySwapNumberException extends Exception {
    }
}
