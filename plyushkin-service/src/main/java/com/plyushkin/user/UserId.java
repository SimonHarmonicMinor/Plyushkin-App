package com.plyushkin.user;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.common.PrefixedId;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class UserId extends PrefixedId {

  protected UserId(@NonNull String prefix) {
    super(prefix);
  }

  protected UserId(@NonNull String prefix, @NonNull String value)
      throws InvalidPrefixedIdException {
    super(prefix, value);
  }

  public static UserId create(String value) throws InvalidUserIdException {
    try {
      return new UserId("U", value);
    } catch (InvalidPrefixedIdException e) {
      throw new InvalidUserIdException(
          "Invalid UserId", e
      );
    }
  }

  public static UserId createRandom() {
    return new UserId("U");
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static class InvalidUserIdException extends Exception {

    public InvalidUserIdException(String message) {
      super(message);
    }

    public InvalidUserIdException(String message, Throwable cause) {
      super(message, cause);
    }
  }
}
