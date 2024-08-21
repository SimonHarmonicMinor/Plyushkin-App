package com.plyushkin.income.domain;

import com.plyushkin.budget.domain.AbstractCategory;
import com.plyushkin.income.IncomeCategoryNumber;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "income_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Setter(PROTECTED)
@Getter
public class IncomeCategory extends AbstractCategory<IncomeCategory> {
    @Convert(converter = IncomeCategoryNumberAttributeConverter.class)
    @ToString.Include
    protected IncomeCategoryNumber number;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Nullable
    private IncomeCategory parent;

    public static IncomeCategory create(IncomeCategoryNumber number,
                                        String name,
                                        WalletId walletId,
                                        UserId whoCreated) {
        IncomeCategory incomeCategory = new IncomeCategory();
        incomeCategory.number = number;
        incomeCategory.name = name;
        incomeCategory.walletId = walletId;
        incomeCategory.whoCreated = whoCreated;
        return incomeCategory;
    }
}
