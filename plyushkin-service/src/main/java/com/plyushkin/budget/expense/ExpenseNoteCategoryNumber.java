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

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
@AllArgsConstructor(access = PRIVATE)
@Getter
@Schema(implementation = Long.class, description = "ExpenseNoteCategoryNumber", minimum = "1")
public class ExpenseNoteCategoryNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static ExpenseNoteCategoryNumber createOne() {
        return new ExpenseNoteCategoryNumber(1L);
    }

    public static ExpenseNoteCategoryNumber create(long value) throws InvalidExpenseNoteCategoryNumberException {
        if (value <= 0) {
            throw new InvalidExpenseNoteCategoryNumberException(
                    "Value should be positive but it is: " + value,
                    value
            );
        }
        ExpenseNoteCategoryNumber expenseNoteCategoryId = new ExpenseNoteCategoryNumber();
        expenseNoteCategoryId.value = value;
        return expenseNoteCategoryId;
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
