package com.plyushkin.budget.expense;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ExpenseNoteId {

  @Column(name = "id")
  private long value;

  public static ExpenseNoteId create(long value) throws InvalidExpenseNoteIdException {
    if (value <= 0) {
      throw new InvalidExpenseNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    ExpenseNoteId expenseNoteId = new ExpenseNoteId();
    expenseNoteId.value = value;
    return expenseNoteId;
  }

  public static class InvalidExpenseNoteIdException extends Exception {

    public InvalidExpenseNoteIdException(String message) {
      super(message);
    }
  }
}
