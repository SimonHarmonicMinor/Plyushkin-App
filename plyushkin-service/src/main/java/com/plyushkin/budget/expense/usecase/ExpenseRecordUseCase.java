package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.base.AbstractRecord;
import com.plyushkin.budget.base.usecase.AbstractRecordUseCase;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.ExpenseRecord;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.repository.ExpenseRecordRepository;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseRecordUseCase extends AbstractRecordUseCase<
        ExpenseCategory,
        ExpenseRecord,
        ExpenseNumber,
        ExpenseCategoryNumber
        > {

    public ExpenseRecordUseCase(ExpenseRecordRepository repository,
                                ExpenseCategoryRepository categoryRepository) {
        super(repository, categoryRepository);
    }

    @Override
    protected ExpenseRecord newRecord(ExpenseNumber number,
                                      WalletId walletId,
                                      UserId whoCreated,
                                      LocalDate date,
                                      Currency currency,
                                      Money amount,
                                      ExpenseCategory category,
                                      String comment) throws AbstractRecord.InvalidRecordException {
        return ExpenseRecord.create(
                number,
                walletId,
                whoCreated,
                date,
                currency,
                amount,
                category,
                comment
        );
    }
}
