package com.plyushkin.wallet;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.util.PrefixedId;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class WalletId extends PrefixedId {

  private WalletId(String prefix) throws InvalidPrefixedIdException {
    super(prefix);
  }

  private WalletId(String prefix, String value)
      throws InvalidPrefixedIdException {
    super(prefix, value);
  }

  public static WalletId createRandom() {
    try {
      return new WalletId("WA");
    } catch (InvalidPrefixedIdException e) {
      throw new IllegalArgumentException("Invalid random WalletId", e);
    }
  }

  public static WalletId create(String value) throws InvalidWalletIdException {
    try {
      return new WalletId("WA", value);
    } catch (InvalidPrefixedIdException e) {
      throw new InvalidWalletIdException("Invalid WalletId", e);
    }
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static class InvalidWalletIdException extends Exception {

    public InvalidWalletIdException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
