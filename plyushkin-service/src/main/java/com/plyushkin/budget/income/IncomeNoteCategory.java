package com.plyushkin.budget.income;

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
@Table(schema = "budget", name = "income_note_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
public class IncomeNoteCategory extends AbstractCategory<IncomeNoteCategoryNumber, IncomeNoteCategory> {

  public static IncomeNoteCategory create(
      IncomeNoteCategoryNumber id,
      String name,
      WalletId walletId,
      UserId whoCreated
  ) {
    IncomeNoteCategory incomeNoteCategory = new IncomeNoteCategory();
    incomeNoteCategory.number = id;
    incomeNoteCategory.name = name;
    incomeNoteCategory.walletId = walletId;
    incomeNoteCategory.whoCreated = whoCreated;
    incomeNoteCategory.children = new HashSet<>();
    return incomeNoteCategory;
  }
}
