package com.plyushkin.budget;

import com.plyushkin.budget.usecase.exception.CategoryNotFoundException;
import com.plyushkin.budget.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.usecase.exception.DeleteCategoryException;
import com.plyushkin.budget.usecase.exception.UpdateCategoryUseCaseException;
import com.plyushkin.infra.DefaultErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
class ExpenseIncomeExceptionResolver {

    @ExceptionHandler(UpdateCategoryUseCaseException.class)
    public ResponseEntity<DefaultErrorResponse> handleUpdateExpenseNoteCategoryException(
            UpdateCategoryUseCaseException e
    ) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new DefaultErrorResponse(e));
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<DefaultErrorResponse> handleCreateCategoryException(CreateCategoryException e) {
        log4xx(e);
        return switch (e) {
            case CreateCategoryException.NonUniqueNamePerWalletId err -> ResponseEntity.status(400)
                    .body(new DefaultErrorResponse(err));
        };
    }

    @ExceptionHandler(DeleteCategoryException.class)
    public ResponseEntity<DefaultErrorResponse> handleDeleteCategoryException(DeleteCategoryException e) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new DefaultErrorResponse(e));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<DefaultErrorResponse> handleCategoryNotFoundException(CategoryNotFoundException e) {
        log4xx(e);
        return ResponseEntity.status(404)
                .body(new DefaultErrorResponse(e));
    }

    private static void log4xx(Throwable e) {
        log.warn("Handled 4xx", e);
    }
}
