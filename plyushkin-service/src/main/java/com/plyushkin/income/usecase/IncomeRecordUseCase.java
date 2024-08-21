package com.plyushkin.income.usecase;

import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.budget.domain.AbstractRecord;
import com.plyushkin.budget.usecase.AbstractRecordUseCase;
import com.plyushkin.income.domain.IncomeCategory;
import com.plyushkin.income.IncomeCategoryNumber;
import com.plyushkin.income.IncomeNumber;
import com.plyushkin.income.domain.IncomeRecord;
import com.plyushkin.income.repository.IncomeCategoryRepository;
import com.plyushkin.income.repository.IncomeRecordRepository;
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