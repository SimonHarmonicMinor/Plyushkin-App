package com.plyushkin.budget.base.usecase.command;

import com.plyushkin.budget.base.AbstractNumber;
import jakarta.annotation.Nullable;

public record UpdateCategoryCommand<N extends AbstractNumber<N>>(
        @Nullable String name,
        @Nullable N parentCategoryNumber
) {
}
