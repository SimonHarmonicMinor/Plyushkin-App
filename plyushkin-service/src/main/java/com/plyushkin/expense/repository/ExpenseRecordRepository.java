package com.plyushkin.expense.repository;

import com.plyushkin.shared.budget.repository.AbstractRecordRepository;
import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.shared.ExpenseNumber;
import com.plyushkin.expense.domain.ExpenseRecord;
import lombok.SneakyThrows;

public interface ExpenseRecordRepository extends AbstractRecordRepository<
        ExpenseCategory,
        ExpenseRecord,
        ExpenseNumber
        > {
    @Override
    @SneakyThrows
    default ExpenseNumber initialNumber() {
        return ExpenseNumber.create(1);
    }
}
