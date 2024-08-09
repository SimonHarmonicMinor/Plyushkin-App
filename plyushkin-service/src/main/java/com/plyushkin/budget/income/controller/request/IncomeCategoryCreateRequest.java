package com.plyushkin.budget.income.controller.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record IncomeCategoryCreateRequest(
        @NotNull(message = "Name cannot be null")
        @Size(min = 1, max = 200, message = "Name should be in range {min} and {max} but actual value is: ${value}")
        String name
) {
}
