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

@Entity
@Table(name = "income_note")
@NoArgsConstructor(access = PROTECTED)
public class IncomeNote extends
    AbstractNote<IncomeNoteId, IncomeNoteCategoryId, IncomeNoteCategory, IncomeNote> {

  protected IncomeNote(
      IncomeNoteId id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      IncomeNoteCategory category,
      String comment
  ) throws InvalidNoteException {
    super(id, walletId, whoDid, date, amount, category, comment);
  }

  public static IncomeNote create(
      IncomeNoteId id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      IncomeNoteCategory category,
      String comment
  )
      throws InvalidNoteException {
    return new IncomeNote(id, walletId, whoDid, date, amount, category, comment);
  }
}
