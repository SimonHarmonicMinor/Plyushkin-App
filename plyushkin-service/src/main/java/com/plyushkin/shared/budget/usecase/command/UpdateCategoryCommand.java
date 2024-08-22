package com.plyushkin.shared.budget.usecase.command;

import com.plyushkin.shared.budget.domain.AbstractNumber;
import jakarta.annotation.Nullable;

public record UpdateCategoryCommand<N extends AbstractNumber<N>>(
        @Nullable String name,
        @Nullable N parentCategoryNumber
) {
}
