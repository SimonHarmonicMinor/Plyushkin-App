package com.plyushkin.budget.income;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.io.Serial;
import java.io.Serializable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class IncomeCategoryNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static IncomeCategoryNumber create(long value) throws InvalidIncomeNoteIdException {
        if (value <= 0) {
            throw new InvalidIncomeNoteIdException(
                    "Value should be positive but it is: " + value
            );
        }
        IncomeCategoryNumber number = new IncomeCategoryNumber();
        number.value = value;
        return number;
    }

    @StandardException
    public static class InvalidIncomeNoteIdException extends Exception {

    }
}
