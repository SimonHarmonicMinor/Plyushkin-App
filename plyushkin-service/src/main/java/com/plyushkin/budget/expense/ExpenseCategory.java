package com.plyushkin.budget.expense;


import com.plyushkin.budget.base.AbstractCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "expense_category")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter(PROTECTED)
public class ExpenseCategory extends AbstractCategory<ExpenseCategory> {
    @Column(updatable = false)
    @ToString.Include
    @Convert(converter = ExpenseCategoryNumberAttributeConverter.class)
    protected ExpenseCategoryNumber number;

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
