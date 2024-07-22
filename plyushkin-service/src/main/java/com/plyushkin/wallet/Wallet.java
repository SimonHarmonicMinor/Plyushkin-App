package com.plyushkin.wallet;

import com.plyushkin.user.UserId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "wallet", name = "wallet")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
@DynamicUpdate
public class Wallet {

    @EmbeddedId
    private WalletId id;

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", updatable = false))
    private UserId createdBy;

    public static Wallet create(WalletId id, String name) {
        Wallet wallet = new Wallet();
        wallet.id = id;
        wallet.name = name;
        return wallet;
    }

    public void update(String name) {
        this.name = name;
    }
}
