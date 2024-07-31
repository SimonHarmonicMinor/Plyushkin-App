package com.plyushkin.budget.expense.usecase.exception;

import lombok.experimental.StandardException;

@StandardException
public sealed class DeleteCategoryException extends Exception {
    @StandardException
    public static final class NotFound extends DeleteCategoryException {
    }

    @StandardException
    public static final class ReferencedByExpenseRecord extends DeleteCategoryException {
    }
}
