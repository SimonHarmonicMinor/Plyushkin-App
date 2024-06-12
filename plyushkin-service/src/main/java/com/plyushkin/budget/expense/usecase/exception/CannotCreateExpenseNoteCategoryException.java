package com.plyushkin.budget.expense.usecase.exception;

public sealed class CannotCreateExpenseNoteCategoryException extends Exception {

  public CannotCreateExpenseNoteCategoryException(String message) {
    super(message);
  }

  public final static class NonUniqueNamePerWalletId extends
      CannotCreateExpenseNoteCategoryException {

    public NonUniqueNamePerWalletId(String message) {
      super(message);
    }
  }
}
