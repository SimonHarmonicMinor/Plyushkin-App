package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import com.plyushkin.domain.budget.Wallet;
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
public class AbstractCategory extends AbstractEntity {
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    protected Wallet wallet;

    @NotNull
    @ToString.Include
    protected String name;
}
