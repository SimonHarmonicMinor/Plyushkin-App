package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.ExpenseCategoryEntityGraph;
import com.plyushkin.budget.expense.controller.request.CreateExpenseNoteCategoryRequest;
import com.plyushkin.budget.expense.controller.request.UpdateExpenseNoteCategoryRequest;
import com.plyushkin.budget.expense.controller.response.CreateExpenseNoteCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse;
import com.plyushkin.budget.expense.controller.response.ErrorCreateCategoryResponse.ErrorNonUniqueNameResponse;
import com.plyushkin.budget.expense.controller.response.ExpenseCategoryResponse;
import com.plyushkin.budget.expense.controller.response.UpdateExpenseNoteCategoryResponse;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.usecase.ExpenseCategoryUseCase;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.UpdateCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException.NonUniqueNamePerWalletId;
import com.plyushkin.budget.expense.usecase.exception.UpdateExpenseNoteCategoryException;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseCategoryController {

    private final ExpenseCategoryUseCase useCase;
    private final ExpenseCategoryRepository repository;

    @PostMapping("/wallets/{walletId}/expenseCategories")
    @Operation(responses = {
            @ApiResponse(
                    responseCode = "201",
                    content = @Content(schema = @Schema(implementation = CreateExpenseNoteCategoryResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(
                            schema = @Schema(
                                    oneOf = ErrorCreateCategoryResponse.class
                            )
                    )
            )
    })
    public ResponseEntity<CreateExpenseNoteCategoryResponse> createCategory(
            @NotNull @PathVariable WalletId walletId,
            @Valid @RequestBody CreateExpenseNoteCategoryRequest request
    )
            throws CreateCategoryException {
        ExpenseCategoryNumber number = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                walletId,
                UserId.createRandom()
        ));
        return ResponseEntity.created(
                        URI.create("/api/wallets/%s/expenseNoteCategories/%s".formatted(walletId, number.getValue()))
                )
                .body(new CreateExpenseNoteCategoryResponse(number));
    }

    @PatchMapping("/wallets/{walletId}/expenseCategories/{number}")
    public void updateCategory(@NotNull @PathVariable ExpenseCategoryNumber number,
                               @NotNull @PathVariable WalletId walletId,
                               @NotNull @Valid @RequestBody UpdateExpenseNoteCategoryRequest request)
            throws UpdateExpenseNoteCategoryException {
        useCase.update(
                walletId,
                number,
                new UpdateCommand(
                        request.name(),
                        request.newParentNumber()
                )
        );
    }

    @GetMapping("/wallets/{walletId}/expenseCategories/{number}")
    public ResponseEntity<ExpenseCategoryResponse> getCategory(
            @NotNull @PathVariable ExpenseCategoryNumber number,
            @NotNull @PathVariable WalletId walletId
    ) {
        return repository.findByWalletIdAndNumber(
                        walletId,
                        number,
                        ExpenseCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).map(ExpenseCategoryResponse::new)
                .map(ResponseEntity::ok)
                .orElse(
                        ResponseEntity.notFound().build()
                );
    }

    @GetMapping("/wallets/{walletId}/expenseCategories")
    public List<ExpenseCategoryResponse> listCategories(@NotNull @PathVariable WalletId walletId) {
        return repository.findAllByWalletId(
                        walletId,
                        ExpenseCategoryEntityGraph.____()
                                .parent()
                                .____.____()
                ).stream()
                .map(ExpenseCategoryResponse::new)
                .toList();
    }

    @ExceptionHandler(UpdateExpenseNoteCategoryException.class)
    public ResponseEntity<UpdateExpenseNoteCategoryResponse> handleUpdateExpenseNoteCategoryException(
            UpdateExpenseNoteCategoryException e
    ) {
        log4xx(e);
        return ResponseEntity.status(400)
                .body(new UpdateExpenseNoteCategoryResponse(e.getMessage()));
    }

    @ExceptionHandler(CreateCategoryException.class)
    public ResponseEntity<ErrorCreateCategoryResponse> handleCreateCategoryException(CreateCategoryException e) {
        log.warn("Handled ErrorCreateCategoryResponse", e);
        return switch (e) {
            case NonUniqueNamePerWalletId err -> ResponseEntity.status(400)
                    .body(new ErrorNonUniqueNameResponse(err.getName()));
        };
    }

    private static void log4xx(Throwable e) {
        log.warn("Handled 4xx", e);
    }
}
