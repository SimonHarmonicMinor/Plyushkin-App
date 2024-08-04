package com.plyushkin.budget.income;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
@Schema(implementation = Long.class, description = "IncomeNumber", minimum = "1")
public class IncomeNumber {
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
    public String toString() {
        return String.valueOf(value);
    }

    @StandardException
    public static class InvalidIncomeNumberException extends Exception {
    }
}
