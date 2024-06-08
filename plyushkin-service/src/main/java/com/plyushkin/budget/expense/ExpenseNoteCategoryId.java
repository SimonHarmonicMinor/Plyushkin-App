package com.plyushkin.budget.expense;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode
public class ExpenseNoteCategoryId {
  @Column(name = "id")
  private long value;

  public static ExpenseNoteCategoryId create(long value) throws InvalidExpenseNoteIdException {
    if (value <= 0) {
      throw new InvalidExpenseNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    ExpenseNoteCategoryId expenseNoteCategoryId = new ExpenseNoteCategoryId();
    expenseNoteCategoryId.value = value;
    return expenseNoteCategoryId;
  }

  public static class InvalidExpenseNoteIdException extends Exception {

    public InvalidExpenseNoteIdException(String message) {
      super(message);
    }
  }
}
