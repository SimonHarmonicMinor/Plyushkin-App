package com.plyushkin.domain;

import java.time.LocalDate;

public class BudgetRecord extends AbstractEntity {
    protected Long id;
    protected LocalDate date;
    protected String comment;
    protected Currency currency;
    protected Money price;
}
