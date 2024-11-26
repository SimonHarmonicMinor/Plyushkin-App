package com.plyushkin.domain.budget.investment.share;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.Operation;
import com.plyushkin.domain.value.Money;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@DiscriminatorValue("SHARE")
@ToString(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Share extends BudgetRecord<Share> {
    @ManyToOne(fetch = LAZY)
    @NotNull
    @JoinColumn(name = "share_company_id")
    private Company company;

    @Min(1)
    @ToString.Include
    @Column(name = "share_count")
    private int count;

    @NotNull
    @ToString.Include
    @Column(name = "share_fee")
    private Money fee;

    @NotNull
    @Enumerated(STRING)
    @ToString.Include
    @Column(name = "share_operation")
    private Operation operation;
}
