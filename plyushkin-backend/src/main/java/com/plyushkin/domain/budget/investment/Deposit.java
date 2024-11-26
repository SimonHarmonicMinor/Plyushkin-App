package com.plyushkin.domain.budget.investment;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.Money;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("DEPOSIT")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Deposit extends BudgetRecord<Deposit> {
    @NotNull
    @Column(name = "deposit_closed_date")
    @ToString.Include
    private LocalDate closedDate;

    @NotNull
    @Column(name = "deposit_closed_amount")
    @ToString.Include
    private Money closedAmount;
}
