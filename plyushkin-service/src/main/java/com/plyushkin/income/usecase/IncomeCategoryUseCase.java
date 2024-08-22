package com.plyushkin.income.usecase;

import com.plyushkin.shared.budget.usecase.AbstractCategoryUseCase;
import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.shared.IncomeCategoryNumber;
import com.plyushkin.income.repository.IncomeCategoryRepository;
import com.plyushkin.income.repository.IncomeRecordRepository;
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
