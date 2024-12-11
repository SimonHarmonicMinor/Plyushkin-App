package com.plyushkin.domain.budget;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("CURRENCY_SWAP")
@Getter
public class CurrencySwap extends BudgetRecord {
    @ToString.Include
    @NotNull
    @Column(name = "swap_bought_amount")
    protected Money boughtAmount;

    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "swap_bought_currency_id")
    protected Currency boughtCurrency;

    public CurrencySwap(UUID id,
                        Wallet wallet,
                        LocalDate date,
                        String comment,
                        Currency currency,
                        Money amount,
                        Money boughtAmount,
                        Currency boughtCurrency) {
        super(id, wallet, date, comment, currency, amount);
        this.boughtAmount = boughtAmount;
        this.boughtCurrency = boughtCurrency;
    }
}
