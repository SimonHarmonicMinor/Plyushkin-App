package com.plyushkin.budget.investment.deposit;

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
@Schema(implementation = Long.class, description = "DepositNumber", minimum = "1")
public class DepositNumber implements AbstractNumber<DepositNumber> {
    private final long value;

    public static DepositNumber create(long value) throws InvalidDepositNumberException {
        if (value <= 0) {
            throw new InvalidDepositNumberException(
                    "Value should be positive but it is: " + value
            );
        }
        return new DepositNumber(value);
    }

    @Override
    public DepositNumber increment() {
        return new DepositNumber(this.value + 1);
    }

    @StandardException
    public static class InvalidDepositNumberException extends Exception {
    }
}
