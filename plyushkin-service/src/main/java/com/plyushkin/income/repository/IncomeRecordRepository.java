package com.plyushkin.income.repository;

import com.plyushkin.shared.budget.repository.AbstractRecordRepository;
import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.shared.IncomeNumber;
import com.plyushkin.income.domain.IncomeRecord;
import lombok.SneakyThrows;

public interface IncomeRecordRepository extends AbstractRecordRepository<IncomeCategory, IncomeRecord, IncomeNumber> {
    @Override
    @SneakyThrows
    default IncomeNumber initialNumber() {
        return IncomeNumber.create(1);
    }
}
