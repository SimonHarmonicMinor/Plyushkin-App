package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class IncomeNoteId {

  @Column(name = "id", updatable = false)
  private long value;

  public static IncomeNoteId create(long value) throws InvalidIncomeNoteIdException {
    if (value <= 0) {
      throw new InvalidIncomeNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    IncomeNoteId incomeNoteId = new IncomeNoteId();
    incomeNoteId.value = value;
    return incomeNoteId;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  public static class InvalidIncomeNoteIdException extends Exception {

    public InvalidIncomeNoteIdException(String message) {
      super(message);
    }
  }
}
