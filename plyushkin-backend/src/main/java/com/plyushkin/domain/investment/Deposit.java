package com.plyushkin.domain.investment;

import com.plyushkin.domain.BudgetRecord;
import com.plyushkin.domain.Money;

import java.time.LocalDate;

public class Deposit extends BudgetRecord {
    protected LocalDate closedDate;
    protected Money closedPrice;
}
