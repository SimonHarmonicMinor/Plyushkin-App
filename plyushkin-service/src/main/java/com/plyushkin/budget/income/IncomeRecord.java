package com.plyushkin.budget.income;

import com.plyushkin.budget.AbstractRecord;
import com.plyushkin.budget.Currency;
import com.plyushkin.budget.Money;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "income_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
public class IncomeRecord extends
        AbstractRecord<IncomeCategory, IncomeRecord> {

    @ToString.Include
    @Convert(converter = IncomeNumberAttributeConverter.class)
    private IncomeNumber number;

    protected IncomeRecord(IncomeNumber number,
                           WalletId walletId,
                           UserId whoDid,
                           LocalDate date,
                           Currency currency,
                           Money amount,
                           IncomeCategory category,
                           String comment) throws InvalidRecordException {
        super(walletId, whoDid, date, currency, amount, category, comment);
        this.number = number;
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
