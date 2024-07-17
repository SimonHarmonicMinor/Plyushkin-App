package com.plyushkin.budget.investment;

import com.plyushkin.budget.Money;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.StandardException;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(schema = "budget", name = "deposit")
@NoArgsConstructor(access = PROTECTED)
@DynamicUpdate
@Getter
public class Deposit {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Getter(PRIVATE)
    private Long pk;

    @Embedded
    private DepositNumber number;

    private String name;

    private String comment;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    private WalletId walletId;

    @Embedded
    private DepositState initialState;

    @Embedded
    @Nullable
    private DepositState closedState;

    public static Deposit newDeposit(DepositNumber number,
                                     String name,
                                     String comment,
                                     WalletId walletId,
                                     Money initialAmount,
                                     LocalDate openDate) {
        Deposit deposit = new Deposit();
        deposit.number = number;
        deposit.name = name;
        deposit.comment = comment;
        deposit.walletId = walletId;
        deposit.initialState = new DepositState(initialAmount, openDate);
        return deposit;
    }

    public void close(LocalDate closingDate, Money finalAmount) throws CloseDepositException {
        if (closedState != null) {
            throw new CloseDepositException.AlreadyClosed("Deposit %s is already closed".formatted(this));
        }
        if (initialState.date().isAfter(closingDate)) {
            throw new CloseDepositException.DateBeforeInitial(
                    "Deposit closing date=%s cannot be before initial date=%s"
                            .formatted(closingDate, initialState.date())
            );
        }
        if (initialState.amount().compareTo(finalAmount) > 0) {
            throw new CloseDepositException.InitialAmountGreaterThanFinal(
                    "Initial amount of money=%s cannot be greater than final=%s"
                            .formatted(initialState.amount(), finalAmount)
            );
        }
        this.closedState = new DepositState(finalAmount, closingDate);
    }

    @StandardException
    public static sealed class CloseDepositException extends Exception {
        @StandardException
        public static final class AlreadyClosed extends CloseDepositException {
        }

        @StandardException
        public static final class DateBeforeInitial extends CloseDepositException {
        }

        @StandardException
        public static final class InitialAmountGreaterThanFinal extends CloseDepositException {
        }
    }
}
