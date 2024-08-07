package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.base.repository.AbstractCategoryRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import lombok.SneakyThrows;

public interface ExpenseCategoryRepository extends AbstractCategoryRepository<ExpenseCategory, ExpenseCategoryNumber> {
    @Override
    @SneakyThrows
    default ExpenseCategoryNumber initialNumber() {
        return ExpenseCategoryNumber.create(1);
    }
}
