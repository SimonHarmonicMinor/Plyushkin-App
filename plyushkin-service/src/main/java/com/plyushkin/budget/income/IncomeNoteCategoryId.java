package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class IncomeNoteCategoryId implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "id", updatable = false)
  private long value;

  public static IncomeNoteCategoryId create(long value) throws InvalidIncomeNoteIdException {
    if (value <= 0) {
      throw new InvalidIncomeNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    IncomeNoteCategoryId incomeNoteCategoryId = new IncomeNoteCategoryId();
    incomeNoteCategoryId.value = value;
    return incomeNoteCategoryId;
  }

  public static class InvalidIncomeNoteIdException extends Exception {

    public InvalidIncomeNoteIdException(String message) {
      super(message);
    }
  }
}
