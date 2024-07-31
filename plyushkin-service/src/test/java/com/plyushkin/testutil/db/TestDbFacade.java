package com.plyushkin.testutil.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.support.TransactionTemplate;

@TestComponent
public class TestDbFacade {
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void cleanDatabase() {
        transactionTemplate.executeWithoutResult(t -> JdbcTestUtils.deleteFromTables(
                jdbcTemplate,
                "budget.expense_record",
                "budget.income_record",
                "budget.expense_category",
                "budget.income_category",
                "budget.deposit",
                "wallet.wallet"
        ));
    }
}
