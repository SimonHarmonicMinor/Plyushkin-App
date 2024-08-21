package com.plyushkin.budget.income.controller;


import com.plyushkin.budget.base.PageResult;
import com.plyushkin.budget.base.usecase.command.CreateRecordCommand;
import com.plyushkin.budget.base.usecase.command.ListRecordsCommand;
import com.plyushkin.budget.base.usecase.command.UpdateRecordCommand;
import com.plyushkin.budget.base.usecase.exception.CreateRecordException;
import com.plyushkin.budget.base.usecase.exception.DeleteRecordException;
import com.plyushkin.budget.base.usecase.exception.RecordNotFoundException;
import com.plyushkin.budget.base.usecase.exception.UpdateRecordException;
import com.plyushkin.budget.income.IncomeNumber;
import com.plyushkin.budget.income.controller.request.IncomeRecordCreateRequest;
import com.plyushkin.budget.income.controller.request.IncomeRecordUpdateRequest;
import com.plyushkin.budget.income.controller.response.IncomeRecordResponse;
import com.plyushkin.budget.income.usecase.IncomeRecordUseCase;
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
class IncomeRecordController {
    private final CurrentUserIdProvider currentUserIdProvider;
    private final IncomeRecordUseCase useCase;

    @PostMapping("/wallets/{walletId}/incomeRecords")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ResponseEntity<IncomeRecordResponse> createIncomeRecord(
            @PathVariable
            @NotNull
            WalletId walletId,

            @Valid
            @NotNull
            IncomeRecordCreateRequest request
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
                        "/api/wallets/%s/incomeRecords/%s"
                                .formatted(
                                        walletId,
                                        res.getNumber().getValue()
                                ))

        ).body(new IncomeRecordResponse(res));
    }

    @GetMapping("/wallets/{walletId}/incomeRecords/{incomeNumber}")
    @Transactional(readOnly = true)
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    public IncomeRecordResponse getIncomeRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull IncomeNumber incomeNumber
    ) throws RecordNotFoundException {
        return new IncomeRecordResponse(useCase.getRecord(walletId, incomeNumber));
    }

    @GetMapping("/wallets/{walletId}/incomeRecords")
    @Transactional(readOnly = true)
    @PreAuthorize("@BudgetAuth.hasAccessForWalletView(#walletId)")
    @SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
    public PageResult<IncomeRecordResponse> listIncomeRecords(
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
                        .map(IncomeRecordResponse::new)
        );
    }

    @PatchMapping("/wallets/{walletId}/incomeRecords/{incomeNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public IncomeRecordResponse updateIncomeRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull IncomeNumber incomeNumber,
            @NotNull @Valid IncomeRecordUpdateRequest request
    ) throws UpdateRecordException {
        return new IncomeRecordResponse(
                useCase.updateRecord(
                        new UpdateRecordCommand<>(
                                walletId,
                                incomeNumber,
                                request.date(),
                                request.currency(),
                                request.amount(),
                                request.categoryNumber(),
                                request.comment()
                        )
                )
        );
    }

    @DeleteMapping("/wallets/{walletId}/incomeRecords/{incomeNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteIncomeRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull IncomeNumber incomeNumber
    ) throws DeleteRecordException {
        useCase.deleteRecord(walletId, incomeNumber);
    }
}
