package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.base.repository.AbstractRecordRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.ExpenseRecord;

public interface ExpenseRecordRepository extends AbstractRecordRepository<ExpenseCategory, ExpenseRecord, ExpenseNumber> {
    @Override
    default ExpenseNumber initialNumber() {
        return ExpenseNumber.createOne();
    }
}
