package com.plyushkin.domain.budget.investment.share;

import com.plyushkin.domain.base.AbstractEntity;
import com.plyushkin.domain.value.ID;
import com.plyushkin.domain.budget.Wallet;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "company")
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Company extends AbstractEntity<Company> {
    @EmbeddedId
    @ToString.Include
    private ID<Company> id;

    @NotNull
    @ManyToOne(fetch = LAZY)
    private Wallet wallet;

    @ToString.Include
    @NotNull
    private String name;

    public Company(ID<Company> id, Wallet wallet, String name) {
        this.id = id;
        this.wallet = wallet;
        this.name = name;
    }
}
