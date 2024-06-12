package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.expense.ExpenseNoteCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseNoteCategoryRepository extends JpaRepository<ExpenseNoteCategory, Long> {

}
