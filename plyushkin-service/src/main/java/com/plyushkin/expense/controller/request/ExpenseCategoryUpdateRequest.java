package com.plyushkin.expense.controller.request;

import com.plyushkin.shared.ExpenseCategoryNumber;
import jakarta.annotation.Nullable;

public record ExpenseCategoryUpdateRequest(
        @Nullable String name,
        @Nullable ExpenseCategoryNumber newParentNumber
) {
}
