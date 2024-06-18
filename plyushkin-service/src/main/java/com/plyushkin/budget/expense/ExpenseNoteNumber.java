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
public class ExpenseNoteNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static ExpenseNoteNumber create(long value) throws InvalidExpenseNoteIdException {
        if (value <= 0) {
            throw new InvalidExpenseNoteIdException(
                    "Value should be positive but it is: " + value,
                    value
            );
        }
        ExpenseNoteNumber expenseNoteId = new ExpenseNoteNumber();
        expenseNoteId.value = value;
        return expenseNoteId;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Getter
    public static class InvalidExpenseNoteIdException extends Exception {
        private final long wrongValue;

        public InvalidExpenseNoteIdException(String message, long wrongValue) {
            super(message);
            this.wrongValue = wrongValue;
        }
    }
}
