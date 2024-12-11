package com.plyushkin.domain.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("CURRENCY_SWAP")
@Getter
public class CurrencySwap extends BudgetRecord<CurrencySwap> {
    @ToString.Include
    @NotNull
    @Column(name = "swap_bought_amount")
    protected Money boughtAmount;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "swap_bought_currency_id")
    protected Currency boughtCurrency;
}
