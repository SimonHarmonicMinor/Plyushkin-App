package com.plyushkin.budget.expense.controller.response;

public sealed interface ErrorCreateCategoryResponse {

  record ErrorNonUniqueNameResponse(
      String name
  ) implements ErrorCreateCategoryResponse {

  }
}
