package com.plyushkin.budget.expense.controller.response;

import jakarta.validation.constraints.NotNull;

public record CreateCategoryResponse(@NotNull long number) {

}
