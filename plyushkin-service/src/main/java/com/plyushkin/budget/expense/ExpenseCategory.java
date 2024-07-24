package com.plyushkin.budget.expense;


import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "expense_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
@Setter(PROTECTED)
public class ExpenseCategory extends AbstractCategory<ExpenseCategoryNumber, ExpenseCategory> {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Nullable
    private ExpenseCategory parent;

    public static ExpenseCategory create(ExpenseCategoryNumber number,
                                         String name,
                                         WalletId walletId,
                                         UserId whoCreated) {
        ExpenseCategory expenseCategory = new ExpenseCategory();
        expenseCategory.number = number;
        expenseCategory.name = name;
        expenseCategory.walletId = walletId;
        expenseCategory.whoCreated = whoCreated;
        return expenseCategory;
    }
}
