package com.plyushkin.domain;

import java.time.LocalDate;

public class Deposit extends BudgetRecord {
    protected LocalDate closedDate;
    protected Money closedPrice;
}
