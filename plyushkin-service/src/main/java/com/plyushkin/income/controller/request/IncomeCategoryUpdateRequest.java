package com.plyushkin.income.controller.request;

import com.plyushkin.shared.IncomeCategoryNumber;
import jakarta.annotation.Nullable;

public record IncomeCategoryUpdateRequest(
        @Nullable String name,
        @Nullable IncomeCategoryNumber newParentNumber
) {
}
