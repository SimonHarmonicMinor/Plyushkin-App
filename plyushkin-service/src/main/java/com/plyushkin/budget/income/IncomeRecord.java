package com.plyushkin.budget.income;

import com.plyushkin.budget.AbstractRecord;
import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "income_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class IncomeRecord extends
        AbstractRecord<IncomeNumber, IncomeCategory, IncomeRecord> {

    protected IncomeRecord(IncomeNumber id,
                           WalletId walletId,
                           UserId whoDid,
                           LocalDate date,
                           Currency currency,
                           Money amount,
                           IncomeCategory category,
                           String comment) throws InvalidRecordException {
        super(id, walletId, whoDid, date, currency, amount, category, comment);
    }

    public static IncomeRecord create(IncomeNumber id,
                                      WalletId walletId,
                                      UserId whoDid,
                                      LocalDate date,
                                      Currency currency,
                                      Money amount,
                                      IncomeCategory category,
                                      String comment) throws InvalidRecordException {
        return new IncomeRecord(id, walletId, whoDid, date, currency, amount, category, comment);
    }
}
