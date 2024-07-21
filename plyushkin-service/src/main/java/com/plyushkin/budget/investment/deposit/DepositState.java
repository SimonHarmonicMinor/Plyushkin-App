package com.plyushkin.budget.investment.deposit;

import com.plyushkin.budget.Money;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public record DepositState(
        Money amount,
        LocalDate date
) {

}
