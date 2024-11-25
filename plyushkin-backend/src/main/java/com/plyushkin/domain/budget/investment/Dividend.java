package com.plyushkin.domain.budget.investment;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.wallet.Company;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("DIVIDEND")
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Dividend extends BudgetRecord<Dividend> {
    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "dividend_company_id")
    private Company company;
}
