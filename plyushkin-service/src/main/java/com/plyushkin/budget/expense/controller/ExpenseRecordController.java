package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.base.PageResult;
import com.plyushkin.budget.base.usecase.command.CreateRecordCommand;
import com.plyushkin.budget.base.usecase.command.ListRecordsCommand;
import com.plyushkin.budget.base.usecase.command.UpdateRecordCommand;
import com.plyushkin.budget.base.usecase.exception.CreateRecordException;
import com.plyushkin.budget.base.usecase.exception.DeleteRecordException;
import com.plyushkin.budget.base.usecase.exception.RecordNotFoundException;
import com.plyushkin.budget.base.usecase.exception.UpdateRecordException;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.controller.request.ExpenseRecordCreateRequest;
import com.plyushkin.budget.expense.controller.request.ExpenseRecordUpdateRequest;
import com.plyushkin.budget.expense.controller.response.ExpenseRecordResponse;
import com.plyushkin.budget.expense.usecase.ExpenseRecordUseCase;
import com.plyushkin.user.CurrentUserIdProvider;
import com.plyushkin.infra.WriteTransactional;
import com.plyushkin.wallet.WalletId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseRecordController {
    private final CurrentUserIdProvider currentUserIdProvider;
    private final ExpenseRecordUseCase useCase;

    @PostMapping("/wallets/{walletId}/expenseRecords")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ResponseEntity<ExpenseRecordResponse> createExpenseRecord(
            @PathVariable
            @NotNull
            WalletId walletId,

            @Valid
            @NotNull
            ExpenseRecordCreateRequest request
    ) throws CreateRecordException {
        final var res = useCase.createRecord(
                new CreateRecordCommand<>(
                        walletId,
                        currentUserIdProvider.get(),
                        request.date(),
                        request.currency(),
                        request.amount(),
                        request.categoryNumber(),
                        request.comment()
                )
        );
        return ResponseEntity.created(
                URI.create(
                        "/api/wallets/%s/expenseRecords/%s"
                                .formatted(
                                        walletId,
                                        res.getNumber().getValue()
                                ))

        ).body(new ExpenseRecordResponse(res));
    }

    @GetMapping("/wallets/{walletId}/expenseRecords/{expenseNumber}")
    @Transactional(readOnly = true)
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public ExpenseRecordResponse getExpenseRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseNumber expenseNumber
    ) throws RecordNotFoundException {
        return new ExpenseRecordResponse(useCase.getRecord(walletId, expenseNumber));
    }

    @GetMapping("/wallets/{walletId}/expenseRecords")
    @Transactional(readOnly = true)
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public PageResult<ExpenseRecordResponse> listExpenseRecords(
            @PathVariable @NotNull WalletId walletId,
            @RequestParam @NotNull @Min(0) int pageNumber,
            @RequestParam @NotNull @Min(1) @Max(1000) int pageSize,
            @RequestParam @Nullable LocalDate from,
            @RequestParam @Nullable LocalDate to
    ) {
        return new PageResult<>(
                useCase.listRecords(
                                new ListRecordsCommand(
                                        walletId,
                                        pageNumber,
                                        pageSize,
                                        from,
                                        to
                                )
                        )
                        .map(ExpenseRecordResponse::new)
        );
    }

    @PatchMapping("/wallets/{walletId}/expenseRecords/{expenseNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ExpenseRecordResponse updateExpenseRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseNumber expenseNumber,
            @NotNull @Valid ExpenseRecordUpdateRequest request
    ) throws UpdateRecordException {
        return new ExpenseRecordResponse(
                useCase.updateRecord(
                        new UpdateRecordCommand<>(
                                walletId,
                                expenseNumber,
                                request.date(),
                                request.currency(),
                                request.amount(),
                                request.categoryNumber(),
                                request.comment()
                        )
                )
        );
    }

    @DeleteMapping("/wallets/{walletId}/expenseRecords/{expenseNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteExpenseRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseNumber expenseNumber
    ) throws DeleteRecordException {
        useCase.deleteRecord(walletId, expenseNumber);
    }
}
