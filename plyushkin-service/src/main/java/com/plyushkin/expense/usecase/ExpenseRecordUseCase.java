package com.plyushkin.expense.usecase;

import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.budget.domain.AbstractRecord;
import com.plyushkin.budget.usecase.AbstractRecordUseCase;
import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.expense.ExpenseCategoryNumber;
import com.plyushkin.expense.ExpenseNumber;
import com.plyushkin.expense.domain.ExpenseRecord;
import com.plyushkin.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.expense.repository.ExpenseRecordRepository;
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
