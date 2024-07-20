package com.plyushkin.budget.expense.query;

import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.repository.ExpenseNoteCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseNoteCategoryQuery {
    private final ExpenseNoteCategoryRepository repository;
}
