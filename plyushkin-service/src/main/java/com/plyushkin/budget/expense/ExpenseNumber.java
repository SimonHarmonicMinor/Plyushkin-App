package com.plyushkin.budget.expense;

import com.plyushkin.budget.base.Number;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static lombok.AccessLevel.PROTECTED;

@AllArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@Getter
@Schema(implementation = Long.class, description = "ExpenseNumber", minimum = "1")
public class ExpenseNumber implements Number<ExpenseNumber> {
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
    public ExpenseNumber increment() {
        return new ExpenseNumber(this.value + 1);
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
