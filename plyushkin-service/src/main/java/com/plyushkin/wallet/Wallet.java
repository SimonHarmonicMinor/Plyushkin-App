package com.plyushkin.wallet;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Convert;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
public class Wallet {

  @EmbeddedId
  private WalletId id;

  private String name;

  @Convert(converter = CurrencyAttributeConverter.class)
  private Currency currency;

  public static Wallet create(WalletId id, String name, Currency currency) {
    Wallet wallet = new Wallet();
    wallet.id = id;
    wallet.name = name;
    wallet.currency = currency;
    return wallet;
  }
}
