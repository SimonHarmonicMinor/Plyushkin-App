package com.plyushkin.budget.income;

import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.HashSet;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "budget.income_note_category")
@NoArgsConstructor(access = PROTECTED)
public class IncomeNoteCategory extends AbstractCategory<IncomeNoteCategoryId, IncomeNoteCategory> {

  public static IncomeNoteCategory create(
      IncomeNoteCategoryId id,
      String name,
      WalletId walletId,
      UserId whoCreated
  ) {
    IncomeNoteCategory incomeNoteCategory = new IncomeNoteCategory();
    incomeNoteCategory.id = id;
    incomeNoteCategory.name = name;
    incomeNoteCategory.walletId = walletId;
    incomeNoteCategory.whoCreated = whoCreated;
    incomeNoteCategory.children = new HashSet<>();
    return incomeNoteCategory;
  }
}
