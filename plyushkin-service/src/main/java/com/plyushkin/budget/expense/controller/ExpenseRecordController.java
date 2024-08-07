package com.plyushkin.budget.expense.controller;

import com.plyushkin.budget.base.AbstractRecord;
import com.plyushkin.budget.expense.controller.request.ExpenseRecordCreateRequest;
import com.plyushkin.budget.expense.controller.request.ExpenseRecordUpdateRequest;
import com.plyushkin.budget.expense.controller.response.ExpenseRecordResponse;
import com.plyushkin.budget.expense.controller.response.PageResult;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.ExpenseRecord;
import com.plyushkin.budget.expense.ExpenseRecord_;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.repository.ExpenseRecordRepository;
import com.plyushkin.user.service.CurrentUserIdProvider;
import com.plyushkin.util.WriteTransactional;
import com.plyushkin.wallet.WalletId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.function.Supplier;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Slf4j
class ExpenseRecordController {
    private final CurrentUserIdProvider currentUserIdProvider;
    private final ExpenseRecordRepository repository;
    private final ExpenseCategoryRepository categoryRepository;

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
    ) throws AbstractRecord.InvalidRecordException {
        repository.lockByWalletId(walletId);
        final var number = repository.nextNumber(walletId);

        final var res = repository.save(
                ExpenseRecord.create(
                        number,
                        walletId,
                        currentUserIdProvider.get(),
                        request.date(),
                        request.currency(),
                        request.amount(),
                        categoryRepository.findByWalletIdAndNumber(walletId, request.categoryNumber())
                                .orElseThrow(() -> new AbstractRecord.InvalidRecordException(
                                        "Cannot find category WalletId=%s, number=%s"
                                                .formatted(walletId, number)
                                )),
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
    ) {
        return repository.findByWalletIdAndNumber(walletId, expenseNumber)
                .map(ExpenseRecordResponse::new)
                .orElseThrow(notFoundExceptionSupplier(walletId, expenseNumber));
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
        final var pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(
                Sort.Order.asc(ExpenseRecord_.DATE),
                Sort.Order.asc(ExpenseRecord_.PK)
        ));

        final var specification = new Specification<ExpenseRecord>() {
            @Override
            public Predicate toPredicate(Root<ExpenseRecord> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                root.fetch(ExpenseRecord_.CATEGORY);
                return cb.and(
                        cb.equal(root.get(ExpenseRecord_.WALLET_ID), walletId),
                        from != null ? cb.greaterThanOrEqualTo(root.get(ExpenseRecord_.DATE), from) : cb.conjunction(),
                        to != null ? cb.lessThanOrEqualTo(root.get(ExpenseRecord_.DATE), to) : cb.conjunction()
                );
            }
        };

        Page<ExpenseRecord> res = repository.findAll(specification, pageRequest);
        return new PageResult<>(res.map(ExpenseRecordResponse::new));
    }

    @PatchMapping("/wallets/{walletId}/expenseRecords/{expenseNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public ExpenseRecordResponse updateExpenseRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseNumber expenseNumber,
            @NotNull @Valid ExpenseRecordUpdateRequest request
    ) throws AbstractRecord.InvalidRecordCategoryException {
        final var expenseRecord = repository.findByWalletIdAndNumber(walletId, expenseNumber)
                .orElseThrow(notFoundExceptionSupplier(walletId, expenseNumber));
        expenseRecord.update(
                request.date(),
                request.currency(),
                request.amount(),
                categoryRepository.findByWalletIdAndNumber(walletId, request.categoryNumber())
                        .orElseThrow(() -> new AbstractRecord.InvalidRecordCategoryException(
                                "Cannot find ExpenseCategory by WalletId=%s and ExpenseCategoryNumber=%s"
                                        .formatted(walletId, request.categoryNumber())
                        )),
                request.comment()
        );
        return new ExpenseRecordResponse(repository.save(expenseRecord));
    }

    @DeleteMapping("/wallets/{walletId}/expenseRecords/{expenseNumber}")
    @WriteTransactional
    @PreAuthorize("@BudgetAuth.hasAccessForWalletUpdate(#walletId)")
    public void deleteExpenseRecord(
            @PathVariable @NotNull WalletId walletId,
            @PathVariable @NotNull ExpenseNumber expenseNumber
    ) {
        final var expenseRecord = repository.findByWalletIdAndNumber(walletId, expenseNumber)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find ExpenseRecord by WalletId=%s and ExpenseNumber=%s"
                                .formatted(walletId, expenseNumber)
                ));
        repository.delete(expenseRecord);
    }

    private static Supplier<EntityNotFoundException> notFoundExceptionSupplier(
            WalletId walletId,
            ExpenseNumber expenseNumber
    ) {
        return () -> new EntityNotFoundException(
                "Cannot find ExpenseRecord by WalletId=%s and ExpenseNumber=%s"
                        .formatted(walletId, expenseNumber)
        );
    }
}
