package com.plyushkin.budget.expense;

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
public class ExpenseNoteCategoryNumber implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "number", updatable = false)
  private long value;

  public static ExpenseNoteCategoryNumber create(long value) throws InvalidExpenseNoteIdException {
    if (value <= 0) {
      throw new InvalidExpenseNoteIdException(
          "Value should be positive but it is: " + value
      );
    }
    ExpenseNoteCategoryNumber expenseNoteCategoryId = new ExpenseNoteCategoryNumber();
    expenseNoteCategoryId.value = value;
    return expenseNoteCategoryId;
  }

  public static class InvalidExpenseNoteIdException extends Exception {

    public InvalidExpenseNoteIdException(String message) {
      super(message);
    }
  }
}
