package com.plyushkin.budget;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class Money {

  @Column
  private BigDecimal value;

  public static Money create(BigDecimal value) throws InvalidMoneyException {
    if (value.compareTo(BigDecimal.ZERO) < 0) {
      throw new InvalidMoneyException(
          "Money cannot be less than zero but passed: " + value
      );
    }
    Money money = new Money();
    money.value = value;
    return money;
  }

  public static class InvalidMoneyException extends Exception {

    public InvalidMoneyException(String message) {
      super(message);
    }
  }
}
