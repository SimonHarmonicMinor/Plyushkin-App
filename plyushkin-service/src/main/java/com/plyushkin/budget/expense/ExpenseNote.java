package com.plyushkin.budget.expense;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractNote;
import com.plyushkin.budget.Money;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "expense_note")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
public class ExpenseNote extends
    AbstractNote<ExpenseNoteId, ExpenseNoteCategoryId, ExpenseNoteCategory, ExpenseNote> {

  protected ExpenseNote(
      ExpenseNoteId id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      ExpenseNoteCategory category,
      String comment
  ) throws InvalidNoteException {
    super(id, walletId, whoDid, date, amount, category, comment);
  }

  public static ExpenseNote create(
      ExpenseNoteId id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      ExpenseNoteCategory category,
      String comment
  )
      throws InvalidNoteException {
    return new ExpenseNote(id, walletId, whoDid, date, amount, category, comment);
  }

}
