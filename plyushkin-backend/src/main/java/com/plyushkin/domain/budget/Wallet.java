package com.plyushkin.domain.budget;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "wallet")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
public class Wallet extends AbstractEntity {
    @EmbeddedId
    @EqualsAndHashCode.Include
    @ToString.Include
    private ID<Wallet> id;

    @NotNull
    @ToString.Include
    private String name;
}
