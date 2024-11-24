package com.plyushkin.domain.investment;

import com.plyushkin.domain.BudgetRecord;
import com.plyushkin.domain.Money;

public class Share extends BudgetRecord {
    protected Company company;
    protected int count;
    protected Money fee;
    protected Operation operation;

    public enum Operation {
        BUY, SELL
    }
}
