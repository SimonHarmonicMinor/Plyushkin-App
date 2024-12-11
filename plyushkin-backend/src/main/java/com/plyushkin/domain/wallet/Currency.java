package com.plyushkin.domain.wallet;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "currency")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Getter
public class Currency extends AbstractEntity<Currency> {
    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    private ID<Currency> id;

    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotNull
    @ToString.Include
    private String name;
}
