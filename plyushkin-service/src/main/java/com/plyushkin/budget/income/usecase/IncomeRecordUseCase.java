package com.plyushkin.budget.income.usecase;

import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.budget.base.AbstractRecord;
import com.plyushkin.budget.base.usecase.AbstractRecordUseCase;
import com.plyushkin.budget.income.IncomeCategory;
import com.plyushkin.budget.income.IncomeCategoryNumber;
import com.plyushkin.budget.income.IncomeNumber;
import com.plyushkin.budget.income.IncomeRecord;
import com.plyushkin.budget.income.repository.IncomeCategoryRepository;
import com.plyushkin.budget.income.repository.IncomeRecordRepository;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IncomeRecordUseCase extends AbstractRecordUseCase<
        IncomeCategory,
        IncomeRecord,
        IncomeNumber,
        IncomeCategoryNumber
        > {
    public IncomeRecordUseCase(IncomeRecordRepository repository,
                               IncomeCategoryRepository categoryRepository) {
        super(repository, categoryRepository);
    }

    @Override
    protected IncomeRecord newRecord(IncomeNumber number,
                                     WalletId walletId,
                                     UserId whoCreated,
                                     LocalDate date,
                                     Currency currency,
                                     Money amount,
                                     IncomeCategory category,
                                     String comment) throws AbstractRecord.InvalidRecordException {
        return IncomeRecord.create(
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
