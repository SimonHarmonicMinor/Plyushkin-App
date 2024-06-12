package com.plyushkin.budget.expense.usecase.exception;

import lombok.experimental.StandardException;

@StandardException
public sealed class CannotAddChildExpenseNoteCategoryException extends Exception {

  @StandardException
  public static final class RootCategoryNotFound extends
      CannotAddChildExpenseNoteCategoryException {

  }

  @StandardException
  public static final class ChildCategoryNotFound extends
      CannotAddChildExpenseNoteCategoryException {

  }

  @StandardException
  public static final class MismatchedWalletId extends CannotAddChildExpenseNoteCategoryException {

  }

  @StandardException
  public static final class ChildEqualsToRoot extends CannotAddChildExpenseNoteCategoryException {

  }
}
