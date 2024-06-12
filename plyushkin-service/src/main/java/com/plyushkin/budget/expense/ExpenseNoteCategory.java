package com.plyushkin.budget.expense;


import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.HashSet;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(schema = "budget", name = "expense_note_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class ExpenseNoteCategory extends
    AbstractCategory<ExpenseNoteCategoryNumber, ExpenseNoteCategory> {

  public static ExpenseNoteCategory create(
      ExpenseNoteCategoryNumber id,
      String name,
      WalletId walletId,
      UserId whoCreated
  ) {
    ExpenseNoteCategory expenseNoteCategory = new ExpenseNoteCategory();
    expenseNoteCategory.number = id;
    expenseNoteCategory.name = name;
    expenseNoteCategory.walletId = walletId;
    expenseNoteCategory.whoCreated = whoCreated;
    expenseNoteCategory.children = new HashSet<>();
    return expenseNoteCategory;
  }
}
