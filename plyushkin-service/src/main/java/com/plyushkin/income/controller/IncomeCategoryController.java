package com.plyushkin.income.controller;


import com.plyushkin.budget.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.usecase.command.UpdateCategoryCommand;
import com.plyushkin.budget.usecase.exception.CategoryNotFoundException;
import com.plyushkin.budget.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.usecase.exception.DeleteCategoryException;
import com.plyushkin.budget.usecase.exception.UpdateCategoryUseCaseException;
import com.plyushkin.income.IncomeCategoryNumber;
import com.plyushkin.income.controller.request.IncomeCategoryCreateRequest;
import com.plyushkin.income.controller.request.IncomeCategoryUpdateRequest;
import com.plyushkin.income.controller.response.IncomeCategoryResponse;
import com.plyushkin.income.usecase.IncomeCategoryUseCase;
import com.plyushkin.user.CurrentUserIdProvider;
import com.plyushkin.wallet.WalletId;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class IncomeCategoryController {
    private final IncomeCategoryUseCase useCase;
    private final CurrentUserIdProvider currentUserIdProvider;

    @PostMapping("/wallets/{walletId}/incomeCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ResponseEntity<IncomeCategoryResponse> createIncomeCategory(
            @NotNull @PathVariable WalletId walletId,
            @NotNull @Valid @RequestBody IncomeCategoryCreateRequest request
    )
            throws CreateCategoryException {
        final var category = useCase.createCategory(new CreateCategoryCommand(
                request.name(),
                walletId,
                currentUserIdProvider.get()
        ));
        return ResponseEntity.created(
                        URI.create(
                                "/api/wallets/%s/incomeCategories/%s"
                                        .formatted(
                                                walletId,
                                                category.getNumber().getValue()
                                        ))
                )
                .body(new IncomeCategoryResponse(category));
    }

    @PatchMapping("/wallets/{walletId}/incomeCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public IncomeCategoryResponse updateIncomeCategory(@NotNull @PathVariable IncomeCategoryNumber number,
                                                 @NotNull @PathVariable WalletId walletId,
                                                 @NotNull @Valid @RequestBody IncomeCategoryUpdateRequest request)
            throws UpdateCategoryUseCaseException {
        return new IncomeCategoryResponse(
                useCase.updateCategory(
                        walletId,
                        number,
                        new UpdateCategoryCommand<>(
                                request.name(),
                                request.newParentNumber()
                        )
                )
        );
    }

    @GetMapping("/wallets/{walletId}/incomeCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public IncomeCategoryResponse getIncomeCategory(
            @NotNull @PathVariable IncomeCategoryNumber number,
            @NotNull @PathVariable WalletId walletId
    ) throws CategoryNotFoundException {
        return new IncomeCategoryResponse(useCase.getCategory(walletId, number));
    }

    @GetMapping("/wallets/{walletId}/incomeCategories")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public List<IncomeCategoryResponse> listIncomeCategories(@NotNull @PathVariable WalletId walletId) {
        return useCase.listCategories(walletId)
                .stream()
                .map(IncomeCategoryResponse::new)
                .toList();
    }

    @DeleteMapping("/wallets/{walletId}/incomeCategories/{number}")
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteIncomeCategory(@PathVariable @NotNull WalletId walletId,
                               @PathVariable @NotNull IncomeCategoryNumber number) throws DeleteCategoryException {
        useCase.deleteCategory(walletId, number);
    }
}
