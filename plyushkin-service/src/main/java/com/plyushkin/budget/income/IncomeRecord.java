package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractRecord;
import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(schema = "budget", name = "income_note")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class IncomeRecord extends
        AbstractRecord<IncomeNoteNumber, IncomeNoteCategoryNumber, IncomeNoteCategory, IncomeRecord> {

    protected IncomeRecord(IncomeNoteNumber id,
                           WalletId walletId,
                           UserId whoDid,
                           LocalDate date,
                           Currency currency,
                           Money amount,
                           IncomeNoteCategory category,
                           String comment) throws InvalidRecordException {
        super(id, walletId, whoDid, date, currency, amount, category, comment);
    }

    public static IncomeRecord create(IncomeNoteNumber id,
                                      WalletId walletId,
                                      UserId whoDid,
                                      LocalDate date,
                                      Currency currency,
                                      Money amount,
                                      IncomeNoteCategory category,
                                      String comment) throws InvalidRecordException {
        return new IncomeRecord(id, walletId, whoDid, date, currency, amount, category, comment);
    }
}
