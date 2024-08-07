package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.base.usecase.AbstractCategoryUseCase;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.repository.ExpenseRecordRepository;
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
