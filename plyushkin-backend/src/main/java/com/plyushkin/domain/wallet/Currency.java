package com.plyushkin.domain.wallet;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "currency")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
public class Currency extends AbstractEntity<Currency> {
    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    private ID<Currency> id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    @NotNull
    @ToString.Include
    private ID<Wallet> walletId;

    @NotNull
    @ToString.Include
    private String name;
}
