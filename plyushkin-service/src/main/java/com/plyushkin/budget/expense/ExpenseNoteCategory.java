package com.plyushkin.budget.expense;


import static jakarta.persistence.FetchType.LAZY;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.expense.events.CategoryParentChangedEvent;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name = "expense_note_category")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
public class ExpenseNoteCategory extends AbstractAggregateRoot<ExpenseNoteCategory> {

  @Id
  @Getter(PRIVATE)
  @ToString.Include
  private Long pk;

  @Embedded
  @ToString.Include
  private ExpenseNoteCategoryId id;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "wallet_id"))
  )
  @ToString.Include
  private WalletId walletId;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "who_created_id"))
  )
  @ToString.Include
  private UserId whoCreated;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id")
  private ExpenseNoteCategory parent;

  @OneToMany(mappedBy = "parent")
  private Set<ExpenseNoteCategory> children;

  public static ExpenseNoteCategory create(
      ExpenseNoteCategoryId id,
      WalletId walletId,
      UserId whoCreated
  ) {
    ExpenseNoteCategory expenseNoteCategory = new ExpenseNoteCategory();
    expenseNoteCategory.id = id;
    expenseNoteCategory.walletId = walletId;
    expenseNoteCategory.whoCreated = whoCreated;
    expenseNoteCategory.children = new HashSet<>();
    return expenseNoteCategory;
  }

  public void changeParent(@Nullable ExpenseNoteCategory newParent) throws ChangeParentCategoryException {
    if (newParent != null && !newParent.walletId.equals(walletId)) {
      throw new ChangeParentCategoryException(
          "Cannot add %s because WalletId is not %s"
              .formatted(newParent, walletId)
      );
    }
    ExpenseNoteCategory oldParent = this.parent;
    this.parent = newParent;
    registerEvent(
        new CategoryParentChangedEvent(
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
