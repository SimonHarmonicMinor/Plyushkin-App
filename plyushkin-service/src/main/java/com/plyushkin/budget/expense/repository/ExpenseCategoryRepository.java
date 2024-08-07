package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.base.repository.AbstractCategoryRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;

public interface ExpenseCategoryRepository extends AbstractCategoryRepository<ExpenseCategory, ExpenseCategoryNumber> {
    @Override
    default ExpenseCategoryNumber initialNumber() {
        return ExpenseCategoryNumber.createOne();
    }
}
