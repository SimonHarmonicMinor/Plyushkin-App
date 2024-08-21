package com.plyushkin.expense.controller;

import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.plyushkin.openapi.client.ExpenseRecordResponse;
import com.plyushkin.openapi.client.ExpenseRecordCreateRequest;
import com.plyushkin.openapi.client.ExpenseRecordUpdateRequest;
import com.plyushkin.openapi.client.ExpenseCategoryCreateRequest;
import com.plyushkin.openapi.client.WalletCreateRequest;
import com.plyushkin.openapi.client.CurrencyEnum;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import static com.plyushkin.testutil.CustomMatchers.containsBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentTest
class ExpenseRecordControllerComponentTest {
    @Autowired
    private TestControllers rest;
    @Autowired
    private TestDbFacade db;

    @BeforeEach
    void beforeEach() {
        db.cleanDatabase();
    }

    @Test
    @SneakyThrows
    void shouldCreateExpenseRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.expenseCategoryController()
                        .createExpenseCategory(
                                walletId,
                                new ExpenseCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();

        final var expenseRecord = rest.expenseRecordController()
                .createExpenseRecord(
                        walletId,
                        new ExpenseRecordCreateRequest()
                                .date(LocalDate.of(2000, Month.APRIL, 1))
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                );

        assertEquals(LocalDate.of(2000, Month.APRIL, 1), expenseRecord.getDate());
        assertEquals(BigDecimal.valueOf(14.45), expenseRecord.getAmount());
        assertEquals("", expenseRecord.getComment());
        assertEquals(CurrencyEnum.DOLLAR, expenseRecord.getCurrency());
        assertEquals(categoryNumber, expenseRecord.getCategoryNumber());
        assertEquals(walletId, expenseRecord.getWalletId());
    }

    @Test
    @SneakyThrows
    void shouldUpdateExpenseRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.expenseCategoryController()
                        .createExpenseCategory(
                                walletId,
                                new ExpenseCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        final var newCategoryNumber =
                rest.expenseCategoryController()
                        .createExpenseCategory(
                                walletId,
                                new ExpenseCategoryCreateRequest()
                                        .name("c2")
                        ).getNumber();
        final var expenseRecordNumber = rest.expenseRecordController()
                .createExpenseRecord(
                        walletId,
                        new ExpenseRecordCreateRequest()
                                .date(LocalDate.of(2000, Month.APRIL, 1))
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                ).getCategoryNumber();

        rest.expenseRecordController()
                .updateExpenseRecord(
                        walletId,
                        expenseRecordNumber,
                        new ExpenseRecordUpdateRequest()
                                .amount(BigDecimal.valueOf(214.8945))
                                .currency(CurrencyEnum.RUB)
                                .categoryNumber(newCategoryNumber)
                                .comment("comment")
                                .date(LocalDate.of(2010, Month.APRIL, 1))
                );

        final var expenseRecord = rest.expenseRecordController()
                .getExpenseRecord(walletId, expenseRecordNumber);

        assertEquals(LocalDate.of(2010, Month.APRIL, 1), expenseRecord.getDate());
        assertEquals(BigDecimal.valueOf(214.8945), expenseRecord.getAmount());
        assertEquals("comment", expenseRecord.getComment());
        assertEquals(CurrencyEnum.RUB, expenseRecord.getCurrency());
        assertEquals(newCategoryNumber, expenseRecord.getCategoryNumber());
        assertEquals(walletId, expenseRecord.getWalletId());
    }

    @Test
    @SneakyThrows
    void shouldDeleteExpenseRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.expenseCategoryController()
                        .createExpenseCategory(
                                walletId,
                                new ExpenseCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        final var expenseRecord = rest.expenseRecordController()
                .createExpenseRecord(
                        walletId,
                        new ExpenseRecordCreateRequest()
                                .date(LocalDate.of(2000, Month.APRIL, 1))
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                );

        rest.expenseRecordController().deleteExpenseRecord(walletId, expenseRecord.getCategoryNumber());

        final var page = rest.expenseRecordController().listExpenseRecords(walletId, 0, 100, null, null);

        assertThat(page.getContent(), hasSize(0));
    }

    @Test
    @SneakyThrows
    void shouldReturnExpenseRecordsByRange() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.expenseCategoryController()
                        .createExpenseCategory(
                                walletId,
                                new ExpenseCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        createRecord(walletId, LocalDate.of(2100, Month.APRIL, 1), categoryNumber);
        final var expenseRecord2 = createRecord(walletId, LocalDate.of(2200, Month.APRIL, 1), categoryNumber);
        final var expenseRecord3 = createRecord(walletId, LocalDate.of(2300, Month.APRIL, 1), categoryNumber);
        createRecord(walletId, LocalDate.of(2400, Month.APRIL, 1), categoryNumber);

        final var page = rest.expenseRecordController().listExpenseRecords(
                walletId,
                0,
                100,
                LocalDate.of(2200, Month.APRIL, 1),
                LocalDate.of(2300, Month.APRIL, 1)
        );

        assertThat(page.getContent(), hasSize(2));
        assertEquals(page.getTotalPages(), 1);
        assertThat(
                page.getContent(),
                allOf(
                        containsBy(e -> Objects.equals(e.getNumber(), expenseRecord2.getNumber())),
                        containsBy(e -> Objects.equals(e.getNumber(), expenseRecord3.getNumber()))
                )
        );
    }

    @SneakyThrows
    private ExpenseRecordResponse createRecord(String walletId, LocalDate date, Long categoryNumber) {
        return rest.expenseRecordController()
                .createExpenseRecord(
                        walletId,
                        new ExpenseRecordCreateRequest()
                                .date(date)
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                );
    }
}
