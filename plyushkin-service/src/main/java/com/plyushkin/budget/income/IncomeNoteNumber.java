package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class IncomeNoteNumber implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "number", updatable = false)
    private long value;

    public static IncomeNoteNumber create(long value) throws InvalidIncomeNoteIdException {
        if (value <= 0) {
            throw new InvalidIncomeNoteIdException(
                    "Value should be positive but it is: " + value
            );
        }
        IncomeNoteNumber incomeNoteId = new IncomeNoteNumber();
        incomeNoteId.value = value;
        return incomeNoteId;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @StandardException
    public static class InvalidIncomeNoteIdException extends Exception {
    }
}
