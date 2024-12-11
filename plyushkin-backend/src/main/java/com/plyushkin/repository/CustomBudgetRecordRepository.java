package com.plyushkin.repository;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.budget.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

interface CustomBudgetRecordRepository {
    <T extends BudgetRecord> List<T> findAll(Class<T> clazz,
                                             Wallet wallet,
                                             @Nullable LocalDate from,
                                             @Nullable LocalDate to);
}
