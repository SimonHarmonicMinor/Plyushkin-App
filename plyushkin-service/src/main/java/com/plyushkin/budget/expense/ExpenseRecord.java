package com.plyushkin.budget.expense;

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
@Table(schema = "budget", name = "expense_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
public class ExpenseRecord extends
        AbstractRecord<ExpenseCategory,
                ExpenseRecord> {

    @ToString.Include
    @Convert(converter = ExpenseNumberAttributeConverter.class)
    private ExpenseNumber number;

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
