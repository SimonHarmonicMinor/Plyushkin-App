package com.plyushkin.budget.expense;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import static lombok.AccessLevel.PRIVATE;

@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(implementation = Long.class, description = "ExpenseNoteCategoryNumber", minimum = "1")
public class ExpenseCategoryNumber {
    private final long value;

    public static ExpenseCategoryNumber createOne() {
        return new ExpenseCategoryNumber(1L);
    }

    public static ExpenseCategoryNumber create(long value) throws InvalidExpenseNoteCategoryNumberException {
        if (value <= 0) {
            throw new InvalidExpenseNoteCategoryNumberException(
                    "Value should be positive but it is: " + value,
                    value
            );
        }
        return new ExpenseCategoryNumber(value);
    }

    public ExpenseCategoryNumber increment() {
        return new ExpenseCategoryNumber(this.value + 1);
    }

    @Getter
    public static class InvalidExpenseNoteCategoryNumberException extends Exception {
        private final long wrongValue;

        public InvalidExpenseNoteCategoryNumberException(String message, long wrongValue) {
            super(message);
            this.wrongValue = wrongValue;
        }
    }
}
