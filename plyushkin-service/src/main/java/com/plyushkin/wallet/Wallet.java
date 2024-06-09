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

@Entity
@Table(schema = "wallet", name = "wallet")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
public class Wallet {

  @EmbeddedId
  private WalletId id;

  private String name;

  @Convert(converter = CurrencyAttributeConverter.class)
  private Currency currency;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "created_by", updatable = false))
  private UserId createdBy;

  public static Wallet create(WalletId id, String name, Currency currency) {
    Wallet wallet = new Wallet();
    wallet.id = id;
    wallet.name = name;
    wallet.currency = currency;
    return wallet;
  }
}
