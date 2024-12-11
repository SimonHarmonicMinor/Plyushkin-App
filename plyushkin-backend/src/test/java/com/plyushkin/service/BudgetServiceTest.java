package com.plyushkin.service;

import com.plyushkin.domain.base.AuditorAwareImpl;
import com.plyushkin.domain.budget.*;
import com.plyushkin.domain.value.ID;
import com.plyushkin.testutil.slices.DBTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@DBTest
@Import({BudgetService.class, AuditorAwareImpl.class})
class BudgetServiceTest {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private TestEntityManager em;
    @Autowired
    private TransactionTemplate transactionTemplate;

    @Test
    void shouldGetAllBudgets() {
        final var wallet = transactionTemplate.execute(
                status -> em.persist(new Wallet(ID.random(), "my wallet"))
        );
        transactionTemplate.executeWithoutResult(status -> {
            final var currency = em.persist(new Currency(ID.random(), wallet, "USD"));
            em.persist(new CurrencySwap(
                    UUID.randomUUID(),
                    wallet,
                    LocalDate.now(),
                    "comment",
                    currency,
                    new Money(BigDecimal.ONE),
                    new Money(BigDecimal.TEN),
                    currency
            ));
        });

        final var all = budgetService.getAll(
                CurrencySwap.class,
                new GetBudgetRecordsCommand(
                        wallet.getId(),
                        null,
                        null
                )
        );

        System.out.println(all);
    }
}