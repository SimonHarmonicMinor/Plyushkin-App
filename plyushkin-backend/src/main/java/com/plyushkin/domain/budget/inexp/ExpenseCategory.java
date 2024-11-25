package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.value.ID;
import com.plyushkin.domain.wallet.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "expense_category")
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class ExpenseCategory extends AbstractCategory<ExpenseCategory> {
    public ExpenseCategory(ID<ExpenseCategory> id, ID<Wallet> walletId, String name) {
        super(id, walletId, name);
    }
}
