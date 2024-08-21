package com.plyushkin.investment.deposit;

import com.plyushkin.budget.domain.Money;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.time.LocalDate;

@Embeddable
public record DepositState(
        @Embedded
        Money amount,
        LocalDate date
) {

}
