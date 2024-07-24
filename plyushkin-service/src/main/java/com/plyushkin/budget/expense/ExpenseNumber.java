package com.plyushkin.budget.expense;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class ExpenseNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static ExpenseNumber create(long value) throws InvalidExpenseNumberException {
        if (value <= 0) {
            throw new InvalidExpenseNumberException(
                    "Value should be positive but it is: " + value,
                    value
            );
        }
        ExpenseNumber number = new ExpenseNumber();
        number.value = value;
        return number;
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
