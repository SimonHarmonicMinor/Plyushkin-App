package com.plyushkin.expense.repository;

import com.plyushkin.shared.budget.repository.AbstractCategoryRepository;
import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.shared.ExpenseCategoryNumber;
import lombok.SneakyThrows;

public interface ExpenseCategoryRepository extends AbstractCategoryRepository<ExpenseCategory, ExpenseCategoryNumber> {
    @Override
    @SneakyThrows
    default ExpenseCategoryNumber initialNumber() {
        return ExpenseCategoryNumber.create(1);
    }
}
