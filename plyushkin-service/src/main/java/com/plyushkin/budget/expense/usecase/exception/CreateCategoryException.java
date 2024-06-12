package com.plyushkin.budget.expense.usecase.exception;

import lombok.Getter;

public sealed class CreateCategoryException extends Exception {

  public CreateCategoryException(String message) {
    super(message);
  }

  @Getter
  public static final class NonUniqueNamePerWalletId extends
      CreateCategoryException {

    private final String name;

    public NonUniqueNamePerWalletId(String message, String name) {
      super(message);
      this.name = name;
    }
  }
}
