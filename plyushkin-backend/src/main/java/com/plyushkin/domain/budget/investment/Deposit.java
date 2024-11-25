package com.plyushkin.domain.budget.investment;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.Money;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("DEPOSIT")
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Deposit extends BudgetRecord<Deposit> {
    @NotNull
    private LocalDate closedDate;
    @NotNull
    private Money closedPrice;
}
