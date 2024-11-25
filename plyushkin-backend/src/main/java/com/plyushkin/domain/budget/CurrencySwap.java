package com.plyushkin.domain.budget;

import com.plyushkin.domain.value.Money;
import com.plyushkin.domain.wallet.Currency;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("CURRENCY_SWAP")
public class CurrencySwap extends BudgetRecord<CurrencySwap> {
    @ToString.Include
    @NotNull
    protected Money boughtPrice;

    @ToString.Include
    @NotNull
    protected Currency boughtCurrency;
}
