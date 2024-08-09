package com.plyushkin.budget.income.controller;

import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

import com.plyushkin.openapi.client.IncomeRecordResponse;
import com.plyushkin.openapi.client.IncomeRecordCreateRequest;
import com.plyushkin.openapi.client.IncomeRecordUpdateRequest;
import com.plyushkin.openapi.client.IncomeCategoryCreateRequest;
import com.plyushkin.openapi.client.WalletCreateRequest;
import com.plyushkin.openapi.client.CurrencyEnum;

import static com.plyushkin.testutil.CustomMatchers.containsBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@ComponentTest
class IncomeRecordControllerTest {
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
    void shouldCreateIncomeRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.incomeCategoryController()
                        .createIncomeCategory(
                                walletId,
                                new IncomeCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();

        final var expenseRecord = rest.incomeRecordController()
                .createIncomeRecord(
                        walletId,
                        new IncomeRecordCreateRequest()
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
    void shouldUpdateIncomeRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.incomeCategoryController()
                        .createIncomeCategory(
                                walletId,
                                new IncomeCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        final var newCategoryNumber =
                rest.incomeCategoryController()
                        .createIncomeCategory(
                                walletId,
                                new IncomeCategoryCreateRequest()
                                        .name("c2")
                        ).getNumber();
        final var expenseRecordNumber = rest.incomeRecordController()
                .createIncomeRecord(
                        walletId,
                        new IncomeRecordCreateRequest()
                                .date(LocalDate.of(2000, Month.APRIL, 1))
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                ).getCategoryNumber();

        rest.incomeRecordController()
                .updateIncomeRecord(
                        walletId,
                        expenseRecordNumber,
                        new IncomeRecordUpdateRequest()
                                .amount(BigDecimal.valueOf(214.8945))
                                .currency(CurrencyEnum.RUB)
                                .categoryNumber(newCategoryNumber)
                                .comment("comment")
                                .date(LocalDate.of(2010, Month.APRIL, 1))
                );

        final var expenseRecord = rest.incomeRecordController()
                .getIncomeRecord(walletId, expenseRecordNumber);

        assertEquals(LocalDate.of(2010, Month.APRIL, 1), expenseRecord.getDate());
        assertEquals(BigDecimal.valueOf(214.8945), expenseRecord.getAmount());
        assertEquals("comment", expenseRecord.getComment());
        assertEquals(CurrencyEnum.RUB, expenseRecord.getCurrency());
        assertEquals(newCategoryNumber, expenseRecord.getCategoryNumber());
        assertEquals(walletId, expenseRecord.getWalletId());
    }

    @Test
    @SneakyThrows
    void shouldDeleteIncomeRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.incomeCategoryController()
                        .createIncomeCategory(
                                walletId,
                                new IncomeCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        final var expenseRecord = rest.incomeRecordController()
                .createIncomeRecord(
                        walletId,
                        new IncomeRecordCreateRequest()
                                .date(LocalDate.of(2000, Month.APRIL, 1))
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                );

        rest.incomeRecordController().deleteIncomeRecord(walletId, expenseRecord.getCategoryNumber());

        final var page = rest.incomeRecordController().listIncomeRecords(walletId, 0, 100, null, null);

        assertThat(page.getContent(), hasSize(0));
    }

    @Test
    @SneakyThrows
    void shouldReturnExpenseRecordsByRange() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var categoryNumber =
                rest.incomeCategoryController()
                        .createIncomeCategory(
                                walletId,
                                new IncomeCategoryCreateRequest()
                                        .name("c1")
                        ).getNumber();
        createRecord(walletId, LocalDate.of(2100, Month.APRIL, 1), categoryNumber);
        final var expenseRecord2 = createRecord(walletId, LocalDate.of(2200, Month.APRIL, 1), categoryNumber);
        final var expenseRecord3 = createRecord(walletId, LocalDate.of(2300, Month.APRIL, 1), categoryNumber);
        createRecord(walletId, LocalDate.of(2400, Month.APRIL, 1), categoryNumber);

        final var page = rest.incomeRecordController().listIncomeRecords(
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
    private IncomeRecordResponse createRecord(String walletId, LocalDate date, Long categoryNumber) {
        return rest.incomeRecordController()
                .createIncomeRecord(
                        walletId,
                        new IncomeRecordCreateRequest()
                                .date(date)
                                .amount(BigDecimal.valueOf(14.45))
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .categoryNumber(categoryNumber)
                );
    }
}