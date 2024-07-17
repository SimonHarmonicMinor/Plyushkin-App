package com.plyushkin.budget.investment;

import com.plyushkin.budget.Money;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record DepositState(
        Money amount,
        LocalDate date
) {

}
