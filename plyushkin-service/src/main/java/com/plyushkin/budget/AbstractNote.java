package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.StandardException;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class AbstractNote<
        I extends Serializable,
        CI extends Serializable,
        C extends AbstractCategory<CI, C>,
        T extends AbstractNote<I, CI, C, T>
        >
        extends AbstractAggregateRoot<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(PRIVATE)
    private Long pk;

    @Embedded
    @ToString.Include
    private I number;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    )
    @ToString.Include
    private WalletId walletId;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "who_did_id"))
    )
    @ToString.Include
    private UserId whoDid;

    @ToString.Include
    private LocalDate date;

    @Convert(converter = CurrencyAttributeConverter.class)
    private Currency currency;

    @Embedded
    @ToString.Include
    @AttributeOverride(name = "value", column = @Column(name = "amount"))
    private Money amount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private C category;

    @ToString.Include
    private String comment;

    @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
    protected AbstractNote(I id,
                           WalletId walletId,
                           UserId whoDid,
                           LocalDate date,
                           Currency currency,
                           Money amount,
                           C category,
                           String comment) throws InvalidNoteException {
        validateCategory(category);
        this.number = id;
        this.walletId = walletId;
        this.whoDid = whoDid;
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.category = category;
        this.comment = comment;
    }

    public void update(LocalDate date,
                       Currency currency,
                       Money amount,
                       C category,
                       String comment) throws InvalidNoteCategoryException {
        validateCategory(category);
        this.date = date;
        this.currency = currency;
        this.amount = amount;
        this.category = category;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AbstractNote abstractNote) {
            return pk != null && pk.equals(abstractNote.pk);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private void validateCategory(C category) throws InvalidNoteCategoryException {
        if (!category.getWalletId().equals(walletId)) {
            throw new InvalidNoteCategoryException(
                    "Category '%s' cannot be assigned because WalletId does not match: %s"
                            .formatted(category, walletId)
            );
        }
    }

    @StandardException
    public static class InvalidNoteException extends Exception {
    }

    @StandardException
    public static class InvalidNoteCategoryException extends InvalidNoteException {
    }
}
