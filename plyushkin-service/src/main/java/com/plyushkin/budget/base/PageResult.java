package com.plyushkin.budget.base;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageResult<T>(
        @NotNull
        List<T> content,
        @NotNull
        int totalPages
) {
    public PageResult(Page<T> page) {
        this(page.getContent(), page.getTotalPages());
    }
}
