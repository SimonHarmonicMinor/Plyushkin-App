package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class AbstractCategory<I, T extends AbstractCategory<I, T>> extends
    AbstractAggregateRoot<T> {

  @Id
  @Getter(PRIVATE)
  @ToString.Include
  protected Long pk;

  @Embedded
  @ToString.Include
  protected I id;

  @ToString.Include
  protected String name;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "wallet_id"))
  )
  @ToString.Include
  protected WalletId walletId;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "who_created_id"))
  )
  @ToString.Include
  protected UserId whoCreated;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id")
  protected T parent;

  @OneToMany(mappedBy = "parent")
  protected Set<T> children;

  public void changeParent(@Nullable T newParent) throws ChangeParentCategoryException {
    if (newParent != null && !newParent.walletId.equals(walletId)) {
      throw new ChangeParentCategoryException(
          "Cannot set parent %s because WalletId is not %s"
              .formatted(newParent, walletId)
      );
    }
    T oldParent = this.parent;
    this.parent = newParent;
    registerEvent(
        new CategoryParentChangedEvent<>(
            this.walletId,
            this.id,
            ofNullable(newParent).map(c -> c.id).orElse(null),
            ofNullable(oldParent).map(c -> c.id).orElse(null)
        )
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof ExpenseNoteCategory expenseNoteCategory) {
      return id != null && Objects.equals(id, expenseNoteCategory.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public static class ChangeParentCategoryException extends Exception {

    public ChangeParentCategoryException(String message) {
      super(message);
    }
  }
}
