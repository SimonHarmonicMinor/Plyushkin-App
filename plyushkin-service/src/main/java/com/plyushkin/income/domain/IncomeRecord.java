package com.plyushkin.income.domain;

import com.plyushkin.shared.budget.domain.AbstractRecord;
import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.IncomeNumber;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "income_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
@Setter(PROTECTED)
public class IncomeRecord extends
        AbstractRecord<IncomeCategory, IncomeRecord> {

    @ToString.Include
    @Convert(converter = IncomeNumberAttributeConverter.class)
    private IncomeNumber number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private IncomeCategory category;

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
        this.category = category;
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
