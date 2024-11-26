package com.plyushkin.domain.budget.inexp;

import com.plyushkin.domain.value.ID;
import com.plyushkin.domain.wallet.Wallet;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "income_category")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class IncomeCategory extends AbstractCategory<IncomeCategory> {
}
