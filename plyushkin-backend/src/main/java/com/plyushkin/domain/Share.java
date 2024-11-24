package com.plyushkin.domain;

import java.time.LocalDate;
import java.util.List;

public class Share extends BudgetRecord {
    protected LocalDate soldDate;
    protected Money soldPrice;
    protected long number;
    protected List<Dividend> dividends;
}
