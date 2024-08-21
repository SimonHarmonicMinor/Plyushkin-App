package com.plyushkin.expense.usecase;

import com.plyushkin.budget.usecase.AbstractCategoryUseCase;
import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.expense.ExpenseCategoryNumber;
import com.plyushkin.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.expense.repository.ExpenseRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class ExpenseCategoryUseCase extends AbstractCategoryUseCase<
        ExpenseCategory,
        ExpenseCategoryNumber,
        ExpenseCategoryRepository,
        ExpenseRecordRepository
        > {

    public ExpenseCategoryUseCase(ExpenseCategoryRepository repository,
                                  ExpenseRecordRepository recordRepository) {
        super(
                repository,
                recordRepository,
                (categoryNumber, command) -> ExpenseCategory.create(
                        categoryNumber,
                        command.name(),
                        command.walletId(),
                        command.whoCreated()
                )
        );
    }
}
