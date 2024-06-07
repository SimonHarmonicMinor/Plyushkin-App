package com.plyushkin.wallet;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.common.PrefixedId;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class WalletId extends PrefixedId {

  private WalletId(@NonNull String prefix) {
    super(prefix);
  }

  private WalletId(@NonNull String prefix, @NonNull String value)
      throws InvalidPrefixedIdException {
    super(prefix, value);
  }

  public static WalletId createRandom() {
    return new WalletId("WA");
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
