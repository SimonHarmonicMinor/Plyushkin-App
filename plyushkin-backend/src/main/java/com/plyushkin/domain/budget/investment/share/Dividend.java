package com.plyushkin.domain.budget.investment.share;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("DIVIDEND")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Dividend extends BudgetRecord<Dividend> {
    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "dividend_company_id")
    private Company company;

    @NotNull
    @ToString.Include
    @Column(name = "dividend_tax")
    private Money tax;
}
