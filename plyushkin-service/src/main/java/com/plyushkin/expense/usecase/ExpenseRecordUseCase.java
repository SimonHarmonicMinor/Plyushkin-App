package com.plyushkin.expense.usecase;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.budget.domain.AbstractRecord;
import com.plyushkin.shared.budget.usecase.AbstractRecordUseCase;
import com.plyushkin.expense.domain.ExpenseCategory;
import com.plyushkin.shared.ExpenseCategoryNumber;
import com.plyushkin.shared.ExpenseNumber;
import com.plyushkin.expense.domain.ExpenseRecord;
import com.plyushkin.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.expense.repository.ExpenseRecordRepository;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
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
