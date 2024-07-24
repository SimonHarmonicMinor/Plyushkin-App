package com.plyushkin.budget.expense;

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
@Table(schema = "budget", name = "expense_record")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class ExpenseRecord extends
        AbstractRecord<ExpenseNumber,
                ExpenseCategoryNumber,
                ExpenseCategory,
                ExpenseRecord> {

    protected ExpenseRecord(ExpenseNumber id,
                            WalletId walletId,
                            UserId whoDid,
                            LocalDate date,
                            Currency currency,
                            Money amount,
                            ExpenseCategory category,
                            String comment) throws InvalidRecordException {
        super(id, walletId, whoDid, date, currency, amount, category, comment);
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
