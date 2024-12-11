package com.plyushkin.domain.budget;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.Money;
import com.plyushkin.domain.wallet.Currency;
import com.plyushkin.domain.wallet.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

import static jakarta.persistence.DiscriminatorType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = STRING)
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
public abstract class BudgetRecord<T extends BudgetRecord<T>> extends AbstractEntity<T> {
    @EqualsAndHashCode.Include
    @ToString.Include
    @Id
    protected UUID id;

    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "wallet_id")
    protected Wallet wallet;

    @ToString.Include
    @NotNull
    protected LocalDate date;

    @ToString.Include
    @NotNull
    @Size(max = 200)
    protected String comment;

    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "currency_id")
    protected Currency currency;

    @ToString.Include
    @NotNull
    protected Money amount;
}
