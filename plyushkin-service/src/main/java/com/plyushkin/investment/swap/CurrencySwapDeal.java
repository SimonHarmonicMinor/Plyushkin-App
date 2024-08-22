package com.plyushkin.investment.swap;

import com.plyushkin.shared.WalletId;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;

// TODO ADD Entity
@Getter
public class CurrencySwapDeal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(PRIVATE)
    private Long pk;

    @Convert(converter = CurrencySwapDealNumberAttributeConverter.class)
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
