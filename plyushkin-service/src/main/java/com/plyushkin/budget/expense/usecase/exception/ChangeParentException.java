package com.plyushkin.budget.expense.usecase.exception;

import lombok.experimental.StandardException;

@StandardException
public sealed class ChangeParentException extends Exception {

    @StandardException
    public static final class RootNotFound extends ChangeParentException {

    }

    @StandardException
    public static final class ParentNotFound extends ChangeParentException {

    }

    @StandardException
    public static final class MismatchedWalletId extends ChangeParentException {

    }

    @StandardException
    public static final class ParentEqualsToRoot extends ChangeParentException {

    }
}
