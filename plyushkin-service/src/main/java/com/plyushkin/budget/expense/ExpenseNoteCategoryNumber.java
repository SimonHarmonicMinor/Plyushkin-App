package com.plyushkin.budget.expense;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
@Getter
public class ExpenseNoteCategoryNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static ExpenseNoteCategoryNumber createOne() {
        return new ExpenseNoteCategoryNumber(1L);
    }

    public static ExpenseNoteCategoryNumber create(long value) throws InvalidExpenseNoteIdException {
        if (value <= 0) {
            throw new InvalidExpenseNoteIdException(
                    "Value should be positive but it is: " + value
            );
        }
        ExpenseNoteCategoryNumber expenseNoteCategoryId = new ExpenseNoteCategoryNumber();
        expenseNoteCategoryId.value = value;
        return expenseNoteCategoryId;
    }

    @StandardException
    public static class InvalidExpenseNoteIdException extends Exception {
    }
}
