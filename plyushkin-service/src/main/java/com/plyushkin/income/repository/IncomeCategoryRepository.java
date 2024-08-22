package com.plyushkin.income.repository;

import com.plyushkin.shared.budget.repository.AbstractCategoryRepository;
import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.shared.IncomeCategoryNumber;
import lombok.SneakyThrows;

public interface IncomeCategoryRepository extends AbstractCategoryRepository<IncomeCategory, IncomeCategoryNumber> {
    @Override
    @SneakyThrows
    default IncomeCategoryNumber initialNumber() {
        return IncomeCategoryNumber.create(1);
    }
}
