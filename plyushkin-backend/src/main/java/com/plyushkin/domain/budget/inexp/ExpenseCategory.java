package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.value.ID;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "expense_category")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class ExpenseCategory extends AbstractCategory {
    @EmbeddedId
    @EqualsAndHashCode.Include
    protected ID<ExpenseCategory> id;
}
