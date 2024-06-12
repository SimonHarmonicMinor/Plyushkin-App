package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.expense.ExpenseNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseNoteRepository extends JpaRepository<ExpenseNote, Long> {
}
