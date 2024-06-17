package com.plyushkin.budget.expense.controller.response;

import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse.ErrorNonUniqueNameResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
    type = "object",
    oneOf = ErrorNonUniqueNameResponse.class
)
public sealed interface ErrorCreateCategoryResponse {

  record ErrorNonUniqueNameResponse(
      @NotNull
      String name
  ) implements ErrorCreateCategoryResponse {

  }
}
