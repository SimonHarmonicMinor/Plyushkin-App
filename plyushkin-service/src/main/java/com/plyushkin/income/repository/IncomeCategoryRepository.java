package com.plyushkin.income.repository;

import com.plyushkin.budget.repository.AbstractCategoryRepository;
import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.income.IncomeCategoryNumber;
import lombok.SneakyThrows;

public interface IncomeCategoryRepository extends AbstractCategoryRepository<IncomeCategory, IncomeCategoryNumber> {
    @Override
    @SneakyThrows
    default IncomeCategoryNumber initialNumber() {
        return IncomeCategoryNumber.create(1);
    }
}
