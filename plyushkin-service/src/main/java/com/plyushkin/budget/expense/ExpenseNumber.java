package com.plyushkin.budget.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
@Schema(implementation = Long.class, description = "ExpenseNumber", minimum = "1")
public class ExpenseNumber {
    private final long value;

    public static ExpenseNumber create(long value) throws InvalidExpenseNumberException {
        if (value <= 0) {
            throw new InvalidExpenseNumberException(
                    "Value should be positive but it is: " + value,
                    value
            );
        }
        return new ExpenseNumber(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Getter
    public static class InvalidExpenseNumberException extends Exception {
        private final long wrongValue;

        public InvalidExpenseNumberException(String message, long wrongValue) {
            super(message);
            this.wrongValue = wrongValue;
        }
    }
}
