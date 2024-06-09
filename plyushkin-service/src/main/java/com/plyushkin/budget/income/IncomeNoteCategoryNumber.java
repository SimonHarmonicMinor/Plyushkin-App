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
public class IncomeNoteCategoryNumber implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "number", updatable = false)
  private long value;

  public static IncomeNoteCategoryNumber create(long value) throws InvalidIncomeNoteIdException {
    if (value <= 0) {
      throw new InvalidIncomeNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    IncomeNoteCategoryNumber incomeNoteCategoryId = new IncomeNoteCategoryNumber();
    incomeNoteCategoryId.value = value;
    return incomeNoteCategoryId;
  }

  public static class InvalidIncomeNoteIdException extends Exception {

    public InvalidIncomeNoteIdException(String message) {
      super(message);
    }
  }
}
