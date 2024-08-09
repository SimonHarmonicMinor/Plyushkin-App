package com.plyushkin.budget.income.controller.request;

import com.plyushkin.budget.income.IncomeCategoryNumber;
import jakarta.annotation.Nullable;

public record IncomeCategoryUpdateRequest(
        @Nullable String name,
        @Nullable IncomeCategoryNumber newParentNumber
) {
}
