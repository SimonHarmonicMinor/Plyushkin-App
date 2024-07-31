package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
    boolean existsByCategory(ExpenseCategory category);
}
