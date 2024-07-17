package com.plyushkin.budget.investment;

import com.plyushkin.budget.AbstractNumber;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class DepositNumber extends AbstractNumber {

    protected DepositNumber(long value) throws InvalidNumberException {
        super(value);
    }

    public static DepositNumber create(long value) throws InvalidDepositNumberException {
        try {
            return new DepositNumber(value);
        } catch (InvalidNumberException e) {
            throw new InvalidDepositNumberException(e.getMessage(), e);
        }
    }

    @StandardException
    public static class InvalidDepositNumberException extends InvalidNumberException {
    }
}
