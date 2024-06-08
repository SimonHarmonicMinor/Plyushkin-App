package com.plyushkin.budget.expense;


import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.HashSet;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "expense_note_category")
@NoArgsConstructor(access = PROTECTED)
public class ExpenseNoteCategory extends
    AbstractCategory<ExpenseNoteCategoryId, ExpenseNoteCategory> {

  public static ExpenseNoteCategory create(
      ExpenseNoteCategoryId id,
      String name,
      WalletId walletId,
      UserId whoCreated
  ) {
    ExpenseNoteCategory expenseNoteCategory = new ExpenseNoteCategory();
    expenseNoteCategory.id = id;
    expenseNoteCategory.name = name;
    expenseNoteCategory.walletId = walletId;
    expenseNoteCategory.whoCreated = whoCreated;
    expenseNoteCategory.children = new HashSet<>();
    return expenseNoteCategory;
  }
}