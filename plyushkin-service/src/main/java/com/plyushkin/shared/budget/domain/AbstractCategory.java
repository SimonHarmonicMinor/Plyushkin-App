package com.plyushkin.shared.budget.domain;

import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.StandardException;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class AbstractCategory<T extends AbstractCategory<T>>
        extends AbstractAggregateRoot<T> {
    @ToString.Include
    protected String name;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "wallet_id", updatable = false))
    )
    @ToString.Include
    protected WalletId walletId;

    @Embedded
    @AttributeOverrides(
            @AttributeOverride(name = "value", column = @Column(name = "who_created_id", updatable = false))
    )
    @ToString.Include
    protected UserId whoCreated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter(PRIVATE)
    private Long pk;

    protected abstract void setParent(T parent);

    public void update(@Nullable String name, @Nullable T newParent) throws UpdateCategoryException {
        if (newParent != null && !newParent.walletId.equals(walletId)) {
            throw new UpdateCategoryException.ChangeParent.MismatchedWalletId(
                    "Cannot set parent %s because WalletId is not %s"
                            .formatted(newParent, walletId)
            );
        }
        if (Objects.equals(newParent, this)) {
            throw new UpdateCategoryException.ChangeParent.ParentEqualsToRoot(
                    "Category cannot be a parent to itself"
            );
        }
        if (name != null) {
            this.name = name;
        }
        setParent(newParent);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof AbstractCategory abstractCategory) {
            return pk != null && Objects.equals(pk, abstractCategory.pk);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @StandardException
    public abstract static sealed class UpdateCategoryException extends Exception {
        @StandardException
        public abstract static sealed class ChangeParent extends UpdateCategoryException {
            @StandardException
            public static final class MismatchedWalletId extends ChangeParent {
            }

            @StandardException
            public static final class ParentEqualsToRoot extends ChangeParent {
            }
        }
    }
}
