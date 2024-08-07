package com.plyushkin.budget.income;

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
@Schema(implementation = Long.class, description = "IncomeNumber", minimum = "1")
public class IncomeNumber implements AbstractNumber<IncomeNumber> {
    private final long value;

    public static IncomeNumber create(long value) throws InvalidIncomeNumberException {
        if (value <= 0) {
            throw new InvalidIncomeNumberException(
                    "Value should be positive but it is: " + value
            );
        }
        return new IncomeNumber(value);
    }

    @Override
    public IncomeNumber increment() {
        return new IncomeNumber(this.value + 1);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @StandardException
    public static class InvalidIncomeNumberException extends Exception {
    }
}
