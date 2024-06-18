package com.plyushkin.budget.expense.usecase.exception;

import lombok.Getter;
import lombok.experimental.StandardException;

@StandardException
public abstract sealed class CreateCategoryException extends Exception {

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
