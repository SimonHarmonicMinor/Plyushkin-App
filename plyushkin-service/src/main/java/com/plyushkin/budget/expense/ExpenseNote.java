package com.plyushkin.budget.expense;

import com.plyushkin.budget.AbstractNote;
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
@Table(schema = "budget", name = "expense_note")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class ExpenseNote extends
        AbstractNote<ExpenseNoteNumber,
                ExpenseNoteCategoryNumber,
                ExpenseNoteCategory,
                ExpenseNote> {

    protected ExpenseNote(ExpenseNoteNumber id,
                          WalletId walletId,
                          UserId whoDid,
                          LocalDate date,
                          Money amount,
                          ExpenseNoteCategory category,
                          String comment) throws InvalidNoteException {
        super(id, walletId, whoDid, date, amount, category, comment);
    }

    public static ExpenseNote create(ExpenseNoteNumber id,
                                     WalletId walletId,
                                     UserId whoDid,
                                     LocalDate date,
                                     Money amount,
                                     ExpenseNoteCategory category,
                                     String comment) throws InvalidNoteException {
        return new ExpenseNote(id, walletId, whoDid, date, amount, category, comment);
    }

}
