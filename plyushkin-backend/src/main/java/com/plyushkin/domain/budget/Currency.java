package com.plyushkin.domain.budget;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "currency")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@Getter
@AllArgsConstructor
public class Currency extends AbstractEntity {
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
