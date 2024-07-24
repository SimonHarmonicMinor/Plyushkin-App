package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.expense.ExpenseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseNoteRepository extends JpaRepository<ExpenseRecord, Long> {
}
