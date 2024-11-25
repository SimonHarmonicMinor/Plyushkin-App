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
@Table(name = "company")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
public class Company extends AbstractEntity<Company> {
    @EmbeddedId
    @ToString.Include
    private ID<Company> id;

    @Embedded
    @NotNull
    @ToString.Include
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id"))
    private ID<Wallet> walletId;

    @ToString.Include
    @NotNull
    private String name;

    public Company(ID<Company> id, ID<Wallet> walletId, String name) {
        this.id = id;
        this.walletId = walletId;
        this.name = name;
    }
}
