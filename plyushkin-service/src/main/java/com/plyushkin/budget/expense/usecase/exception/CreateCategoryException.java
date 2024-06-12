package com.plyushkin.budget.expense.usecase.exception;

public sealed class CreateCategoryException extends Exception {

  public CreateCategoryException(String message) {
    super(message);
  }

  public final static class NonUniqueNamePerWalletId extends
      CreateCategoryException {

    public NonUniqueNamePerWalletId(String message) {
      super(message);
    }
  }
}
