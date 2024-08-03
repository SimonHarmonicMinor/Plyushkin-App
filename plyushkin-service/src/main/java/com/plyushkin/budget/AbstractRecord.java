package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Objects.requireNonNullElse;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.annotation.Nullable;
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
public class AbstractRecord<
        I extends Serializable,
        C extends AbstractCategory<C>,
        T extends AbstractRecord<I, C, T>
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
    protected AbstractRecord(I id,
                             WalletId walletId,
                             UserId whoDid,
                             LocalDate date,
                             Currency currency,
                             Money amount,
                             C category,
                             String comment) throws InvalidRecordException {
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

    public void update(@Nullable LocalDate date,
                       @Nullable Currency currency,
                       @Nullable Money amount,
                       @Nullable C category,
                       @Nullable String comment) throws InvalidRecordCategoryException {
        if (category != null) {
            validateCategory(category);
            this.category = category;
        }
        if (date != null) {
            this.date = date;
        }
        if (currency != null) {
            this.currency = currency;
        }
        if (amount != null) {
            this.amount = amount;
        }
        this.comment = requireNonNullElse(comment, "");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AbstractRecord abstractRecord) {
            return pk != null && pk.equals(abstractRecord.pk);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    private void validateCategory(C category) throws InvalidRecordCategoryException {
        if (!category.getWalletId().equals(walletId)) {
            throw new InvalidRecordCategoryException(
                    "Category '%s' cannot be assigned because WalletId does not match: %s"
                            .formatted(category, walletId)
            );
        }
    }

    @StandardException
    public static class InvalidRecordException extends Exception {
    }

    @StandardException
    public static class InvalidRecordCategoryException extends InvalidRecordException {
    }
}
