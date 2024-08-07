package com.plyushkin.budget.income.usecase;

import com.plyushkin.budget.base.usecase.AbstractCategoryUseCase;
import com.plyushkin.budget.income.IncomeCategory;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import com.plyushkin.budget.income.repository.IncomeCategoryRepository;
import com.plyushkin.budget.income.repository.IncomeRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class IncomeCategoryUseCase extends AbstractCategoryUseCase<
        IncomeCategory,
        IncomeCategoryNumber,
        IncomeCategoryRepository,
        IncomeRecordRepository
        > {
    public IncomeCategoryUseCase(IncomeCategoryRepository repository,
                                 IncomeRecordRepository recordRepository) {
        super(repository,
                recordRepository,
                (incomeCategoryNumber, createCategoryCommand) -> IncomeCategory.create(
                        incomeCategoryNumber,
                        createCategoryCommand.name(),
                        createCategoryCommand.walletId(),
                        createCategoryCommand.whoCreated()
                )
        );
    }
}
