package com.plyushkin.income.controller.request;

import com.plyushkin.income.IncomeCategoryNumber;
import jakarta.annotation.Nullable;

public record IncomeCategoryUpdateRequest(
        @Nullable String name,
        @Nullable IncomeCategoryNumber newParentNumber
) {
}
