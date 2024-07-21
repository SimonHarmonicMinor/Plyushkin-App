package com.plyushkin.budget.investment.swap;

import com.plyushkin.wallet.WalletId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(schema = "budget", name = "currency_swap_deal")
@Getter
public class CurrencySwapDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(PRIVATE)
    private Long pk;

    @Embedded
    private CurrencySwapDealNumber number;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    private WalletId walletId;

    private String name;

    private String comment;

    private LocalDate date;

    @Embedded
    private CurrencySwapDealInfo income;

    @Embedded
    private CurrencySwapDealInfo expense;
}
