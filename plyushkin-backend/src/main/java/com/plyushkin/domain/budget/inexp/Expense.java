package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.budget.BudgetRecord;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@DiscriminatorValue("EXPENSE")
@Getter
public class Expense extends BudgetRecord {
    @NotNull
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "expense_category_id")
    private ExpenseCategory category;
}
