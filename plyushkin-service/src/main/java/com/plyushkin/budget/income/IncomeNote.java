package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractNote;
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
public class IncomeNote extends
        AbstractNote<IncomeNoteNumber, IncomeNoteCategoryNumber, IncomeNoteCategory, IncomeNote> {

    protected IncomeNote(IncomeNoteNumber id,
                         WalletId walletId,
                         UserId whoDid,
                         LocalDate date,
                         Money amount,
                         IncomeNoteCategory category,
                         String comment) throws InvalidNoteException {
        super(id, walletId, whoDid, date, amount, category, comment);
    }

    public static IncomeNote create(IncomeNoteNumber id,
                                    WalletId walletId,
                                    UserId whoDid,
                                    LocalDate date,
                                    Money amount,
                                    IncomeNoteCategory category,
                                    String comment) throws InvalidNoteException {
        return new IncomeNote(id, walletId, whoDid, date, amount, category, comment);
    }
}
