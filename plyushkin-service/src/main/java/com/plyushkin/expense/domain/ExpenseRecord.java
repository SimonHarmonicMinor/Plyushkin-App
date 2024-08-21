package com.plyushkin.expense.domain;

import com.plyushkin.budget.domain.AbstractRecord;
import com.plyushkin.budget.domain.Currency;
import com.plyushkin.budget.domain.Money;
import com.plyushkin.expense.ExpenseNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
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
@Table(schema = "budget", name = "expense_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
@Setter(PROTECTED)
public class ExpenseRecord extends
        AbstractRecord<ExpenseCategory, ExpenseRecord> {

    @ToString.Include
    @Convert(converter = ExpenseNumberAttributeConverter.class)
    private ExpenseNumber number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private ExpenseCategory category;

    protected ExpenseRecord(ExpenseNumber number,
                            WalletId walletId,
                            UserId whoDid,
                            LocalDate date,
                            Currency currency,
                            Money amount,
                            ExpenseCategory category,
                            String comment) throws InvalidRecordException {
        super(walletId, whoDid, date, currency, amount, category, comment);
        this.number = number;
        this.category = category;
    }

    public static ExpenseRecord create(ExpenseNumber id,
                                       WalletId walletId,
                                       UserId whoDid,
                                       LocalDate date,
                                       Currency currency,
                                       Money amount,
                                       ExpenseCategory category,
                                       String comment) throws InvalidRecordException {
        return new ExpenseRecord(id, walletId, whoDid, date, currency, amount, category, comment);
    }

}
