package com.plyushkin.wallet.domain;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "wallet", name = "wallet")
@NoArgsConstructor(access = PROTECTED)
@Getter
@DynamicUpdate
public class Wallet {

    @EmbeddedId
    private WalletId id;

    private String name;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_by", updatable = false))
    private UserId createdBy;

    public static Wallet create(WalletId id, String name, UserId createdBy) {
        Wallet wallet = new Wallet();
        wallet.id = id;
        wallet.name = name;
        wallet.createdBy = createdBy;
        return wallet;
    }

    public void update(@Nullable String name) {
        if (name != null) {
            this.name = name;
        }
    }
}
