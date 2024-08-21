package com.plyushkin.budget.usecase.exception;

import lombok.experimental.StandardException;

@StandardException
public abstract sealed class UpdateCategoryUseCaseException extends Exception {

    @StandardException
    public abstract static sealed class ChangeParent extends UpdateCategoryUseCaseException {
        @StandardException
        public static final class RootNotFound extends ChangeParent {
        }

        @StandardException
        public static final class ParentNotFound extends ChangeParent {
        }

        @StandardException
        public static final class MismatchedWalletId extends ChangeParent {
        }

        @StandardException
        public static final class ParentEqualsToRoot extends ChangeParent {
        }
    }
}
