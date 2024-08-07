package com.plyushkin.budget.income.repository;

import com.plyushkin.budget.base.repository.AbstractCategoryRepository;
import com.plyushkin.budget.income.IncomeCategory;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import lombok.SneakyThrows;

public interface IncomeCategoryRepository extends AbstractCategoryRepository<IncomeCategory, IncomeCategoryNumber> {
    @Override
    @SneakyThrows
    default IncomeCategoryNumber initialNumber() {
        return IncomeCategoryNumber.create(1);
    }
}
