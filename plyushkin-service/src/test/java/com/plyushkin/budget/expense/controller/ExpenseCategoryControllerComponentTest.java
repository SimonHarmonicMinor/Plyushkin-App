package com.plyushkin.budget.expense.controller;

import com.plyushkin.openapi.client.ApiException;
import com.plyushkin.openapi.client.ExpenseCategoryCreateRequest;
import com.plyushkin.openapi.client.ExpenseCategoryUpdateRequest;
import com.plyushkin.openapi.client.ExpenseRecordCreateRequest;
import com.plyushkin.openapi.client.CurrencyEnum;
import com.plyushkin.openapi.client.WalletCreateRequest;
import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.plyushkin.testutil.CustomMatchers.containsBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ComponentTest
@SuppressWarnings("checkstyle:MultipleStringLiterals")
class ExpenseCategoryControllerComponentTest {
    @Autowired
    private TestDbFacade db;
    @Autowired
    private TestControllers rest;

    @BeforeEach
    void beforeEach() {
        db.cleanDatabase();
    }

    @Test
    @SneakyThrows
    void shouldCreateCategory() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();

        final var category = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 1")
                );

        assertEquals(category.getNumber(), 1);
        assertEquals(category.getWalletId(), walletId);
        assertEquals(category.getName(), "category 1");
    }

    @Test
    @SneakyThrows
    void shouldCreateHierarchy() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var category1 = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 1")
                );
        final var category2 = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 2")
                );
        final var category3 = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 3")
                );

        rest.expenseCategoryController()
                .updateExpenseCategory(
                        category2.getNumber(),
                        walletId,
                        new ExpenseCategoryUpdateRequest()
                                .newParentNumber(category1.getNumber())
                );
        rest.expenseCategoryController()
                .updateExpenseCategory(
                        category3.getNumber(),
                        walletId,
                        new ExpenseCategoryUpdateRequest()
                                .newParentNumber(category1.getNumber())
                );

        final var categories =
                rest.expenseCategoryController()
                        .listExpenseCategories(walletId);

        assertThat(categories, allOf(
                hasSize(3),
                containsBy(c -> c.getName().equals("category 1")
                        && c.getParentNumber() == null),
                containsBy(c -> c.getName().equals("category 2")
                        && Objects.equals(c.getParentNumber(), category1.getNumber())),
                containsBy(c -> c.getName().equals("category 3")
                        && Objects.equals(c.getParentNumber(), category1.getNumber()))
        ));
    }

    @Test
    @SneakyThrows
    void shouldDeleteCategory() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var category1 = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 1")
                );

        rest.expenseCategoryController().deleteExpenseCategory(walletId, category1.getNumber());

        final var categories = rest.expenseCategoryController().listExpenseCategories(walletId);
        assertThat(categories, hasSize(0));
    }

    @Test
    @SneakyThrows
    void shouldNotDeleteCategoryIfItUsedByExpenseRecord() {
        final var walletId =
                rest.walletController().createWallet(new WalletCreateRequest().name("w1"))
                        .getId();
        final var category1 = rest.expenseCategoryController()
                .createExpenseCategory(
                        walletId,
                        new ExpenseCategoryCreateRequest()
                                .name("category 1")
                );
        rest.expenseRecordController()
                .createExpenseRecord(
                        walletId,
                        new ExpenseRecordCreateRequest()
                                .categoryNumber(category1.getNumber())
                                .comment("")
                                .currency(CurrencyEnum.DOLLAR)
                                .date(LocalDate.now())
                                .amount(BigDecimal.ONE)
                );

        final var exception = assertThrows(
                ApiException.class,
                () -> rest.expenseCategoryController().deleteExpenseCategory(
                        walletId,
                        category1.getNumber()
                )
        );

        assertEquals(400, exception.getCode());
    }
}
