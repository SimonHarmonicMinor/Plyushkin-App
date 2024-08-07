package com.plyushkin.budget.base.usecase.command;

import com.plyushkin.budget.base.Number;
import jakarta.annotation.Nullable;

public record UpdateCategoryCommand<N extends Number<N>>(
        @Nullable String name,
        @Nullable N parentCategoryNumber
) {
}
