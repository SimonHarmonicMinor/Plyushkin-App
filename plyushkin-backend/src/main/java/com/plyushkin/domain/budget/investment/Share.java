package com.plyushkin.domain.budget.investment;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.Money;
import com.plyushkin.domain.wallet.Company;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("SHARE")
@ToString(callSuper = true)
@NoArgsConstructor(access = PROTECTED)
public class Share extends BudgetRecord<Share> {
    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "share_company_id")
    private Company company;

    @Min(0)
    private int count;

    @NotNull
    private Money fee;

    @NotNull
    @Enumerated(STRING)
    private Operation operation;

    public enum Operation {
        BUY, SELL
    }
}
