package com.plyushkin.budget.income;

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
@Table(schema = "budget", name = "income_note_category")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Setter(PROTECTED)
@Getter
public class IncomeNoteCategory extends AbstractCategory<IncomeNoteCategoryNumber, IncomeNoteCategory> {

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    @Nullable
    private IncomeNoteCategory parent;

    public static IncomeNoteCategory create(IncomeNoteCategoryNumber id,
                                            String name,
                                            WalletId walletId,
                                            UserId whoCreated) {
        IncomeNoteCategory incomeNoteCategory = new IncomeNoteCategory();
        incomeNoteCategory.number = id;
        incomeNoteCategory.name = name;
        incomeNoteCategory.walletId = walletId;
        incomeNoteCategory.whoCreated = whoCreated;
        return incomeNoteCategory;
    }
}
