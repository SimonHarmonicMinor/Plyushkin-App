package com.plyushkin.domain.budget;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
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
    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    protected ID id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    @ToString.Include
    @NotNull
    protected ID<Wallet> walletId;

    @ToString.Include
    @NotNull
    protected LocalDate date;

    @ToString.Include
    @NotNull
    @Size(max = 200)
    protected String comment;

    @ManyToOne(fetch = LAZY)
    @NotNull
    protected Currency currency;

    @ToString.Include
    @NotNull
    protected Money amount;

    public ID<T> getId() {
        return id;
    }
}
