package com.plyushkin.domain.budget;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Objects;

@Schema(implementation = BigDecimal.class, description = "Money")
public record Money(
        BigDecimal value
) implements Comparable<Money> {
    public Money {
        Objects.requireNonNull(value, "Cannot be null");
    }

    @Override
    public int compareTo(Money o) {
        return this.value.compareTo(o.value);
    }

    public Money plus(Money money) {
        return new Money(this.value.add(money.value));
    }

    public Money minus(Money money) {
        return new Money(this.value.subtract(money.value));
    }
}
