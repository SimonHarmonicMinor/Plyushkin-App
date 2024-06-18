package com.plyushkin.budget.expense;


import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.util.HashSet;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "expense_note_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
@Setter(PROTECTED)
public class ExpenseNoteCategory extends AbstractCategory<ExpenseNoteCategoryNumber, ExpenseNoteCategory> {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Nullable
    private ExpenseNoteCategory parent;

    public static ExpenseNoteCategory create(ExpenseNoteCategoryNumber number,
                                             String name,
                                             WalletId walletId,
                                             UserId whoCreated) {
        ExpenseNoteCategory expenseNoteCategory = new ExpenseNoteCategory();
        expenseNoteCategory.number = number;
        expenseNoteCategory.name = name;
        expenseNoteCategory.walletId = walletId;
        expenseNoteCategory.whoCreated = whoCreated;
        return expenseNoteCategory;
    }
}
