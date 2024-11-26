package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import com.plyushkin.domain.wallet.Wallet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class AbstractCategory<T extends AbstractCategory<T>> extends AbstractEntity<T> {
    @EmbeddedId
    @EqualsAndHashCode.Include
    protected ID<T> id;

    @Embedded
    @NotNull
    @ToString.Include
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    protected ID<Wallet> walletId;

    @NotNull
    @ToString.Include
    protected String name;
}
