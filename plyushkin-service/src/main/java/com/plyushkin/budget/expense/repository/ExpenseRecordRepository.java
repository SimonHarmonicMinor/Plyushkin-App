package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.base.repository.AbstractRecordRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.ExpenseRecord;
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
