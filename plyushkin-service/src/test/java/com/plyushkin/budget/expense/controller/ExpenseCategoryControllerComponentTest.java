package com.plyushkin.budget.expense.controller;

import com.plyushkin.openapi.client.ExpenseNoteCategoryCreateRequest;
import com.plyushkin.openapi.client.ExpenseNoteCategoryUpdateRequest;
import com.plyushkin.openapi.client.WalletCreateRequest;
import com.plyushkin.testutil.db.TestDbFacade;
import com.plyushkin.testutil.rest.TestControllers;
import com.plyushkin.testutil.slices.ComponentTest;
import lombok.SneakyThrows;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentTest
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
                .createCategory(
                        walletId,
                        new ExpenseNoteCategoryCreateRequest()
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
                .createCategory(
                        walletId,
                        new ExpenseNoteCategoryCreateRequest()
                                .name("category 1")
                );
        final var category2 = rest.expenseCategoryController()
                .createCategory(
                        walletId,
                        new ExpenseNoteCategoryCreateRequest()
                                .name("category 2")
                );
        final var category3 = rest.expenseCategoryController()
                .createCategory(
                        walletId,
                        new ExpenseNoteCategoryCreateRequest()
                                .name("category 3")
                );

        rest.expenseCategoryController()
                .updateCategory(
                        category2.getNumber(),
                        walletId,
                        new ExpenseNoteCategoryUpdateRequest()
                                .newParentNumber(category1.getNumber())
                );
        rest.expenseCategoryController()
                .updateCategory(
                        category3.getNumber(),
                        walletId,
                        new ExpenseNoteCategoryUpdateRequest()
                                .newParentNumber(category1.getNumber())
                );

        final var categories =
                rest.expenseCategoryController()
                        .listCategories(walletId);

        assertThat(categories, allOf(
                hasSize(3),
                containsBy(c -> c.getName().equals("category 1") && c.getParentNumber() == null),
                containsBy(c -> c.getName().equals("category 2") && Objects.equals(c.getParentNumber(), category1.getNumber())),
                containsBy(c -> c.getName().equals("category 3") && Objects.equals(c.getParentNumber(), category1.getNumber()))
        ));
    }

    private static <T> Matcher<Collection<T>> containsBy(Predicate<T> predicate) {
        return new TypeSafeMatcher<>() {
            @Override
            protected boolean matchesSafely(Collection<T> t) {
                return t.stream().anyMatch(predicate);
            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }
}