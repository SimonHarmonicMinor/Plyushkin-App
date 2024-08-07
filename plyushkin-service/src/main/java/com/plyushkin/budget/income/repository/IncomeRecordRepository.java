package com.plyushkin.budget.income.repository;

import com.plyushkin.budget.base.repository.AbstractRecordRepository;
import com.plyushkin.budget.income.IncomeCategory;
import com.plyushkin.budget.income.IncomeNumber;
import com.plyushkin.budget.income.IncomeRecord;
import lombok.SneakyThrows;

public interface IncomeRecordRepository extends AbstractRecordRepository<IncomeCategory, IncomeRecord, IncomeNumber> {
    @Override
    @SneakyThrows
    default IncomeNumber initialNumber() {
        return IncomeNumber.create(1);
    }
}
