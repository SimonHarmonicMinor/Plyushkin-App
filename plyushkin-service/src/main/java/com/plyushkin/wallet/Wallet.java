package com.plyushkin.wallet;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(schema = "wallet", name = "wallet")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
@DynamicUpdate
public class Wallet {

    @EmbeddedId
    private WalletId id;

    private String name;

    @Convert(converter = CurrencyTypeAttributeConverter.class)
    private CurrencyType currencyType;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", updatable = false))
    private UserId createdBy;

    public static Wallet create(WalletId id, String name, CurrencyType currencyType) {
        Wallet wallet = new Wallet();
        wallet.id = id;
        wallet.name = name;
        wallet.currencyType = currencyType;
        return wallet;
    }

    public void update(String name, CurrencyType currencyType) {
        this.name = name;
        this.currencyType = currencyType;
    }
}
