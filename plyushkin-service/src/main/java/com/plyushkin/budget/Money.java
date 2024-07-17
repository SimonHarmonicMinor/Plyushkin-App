package com.plyushkin.budget;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;

import java.math.BigDecimal;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class Money implements Comparable<Money> {

    @Column
    private BigDecimal value;

    public static Money create(BigDecimal value) throws InvalidMoneyException {
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidMoneyException(
                    "Money cannot be less than zero but passed: " + value
            );
        }
        Money money = new Money();
        money.value = value;
        return money;
    }

    @Override
    public int compareTo(Money o) {
        return this.value.compareTo(o.value);
    }

    @StandardException
    public static class InvalidMoneyException extends Exception {
    }
}
