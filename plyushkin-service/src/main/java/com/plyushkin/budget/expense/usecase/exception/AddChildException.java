package com.plyushkin.budget.expense.usecase.exception;

import lombok.experimental.StandardException;

@StandardException
public sealed class AddChildException extends Exception {

  @StandardException
  public static final class RootCategoryNotFound extends
      AddChildException {

  }

  @StandardException
  public static final class ChildCategoryNotFound extends
      AddChildException {

  }

  @StandardException
  public static final class MismatchedWalletId extends AddChildException {

  }

  @StandardException
  public static final class ChildEqualsToRoot extends AddChildException {

  }
}
