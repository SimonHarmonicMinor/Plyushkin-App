package com.plyushkin.budget.expense;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.budget.Money;
import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name = "expense_note")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
@ToString(onlyExplicitlyIncluded = true)
public class ExpenseNote extends AbstractAggregateRoot<ExpenseNote> {

  @Id
  @Getter(PRIVATE)
  @ToString.Include
  private Long pk;

  @Embedded
  @ToString.Include
  private ExpenseNoteId id;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "wallet_id"))
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

  @Embedded
  @ToString.Include
  private Money amount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private ExpenseNoteCategory category;

  @ToString.Include
  private String comment;

  public static ExpenseNote create(
      ExpenseNoteId id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      ExpenseNoteCategory category,
      String comment
  )
      throws InvalidExpenseNoteException {
    if (category.getChildren().size() != 0) {
      throw new InvalidExpenseNoteCategoryException(
          "Category '%s' cannot be assigned to ExpenseNote because it has children: %s"
              .formatted(category, category.getChildren())
      );
    }
    if (!category.getWalletId().equals(walletId)) {
      throw new InvalidExpenseNoteCategoryException(
          "Category '%s' cannot be assigned to ExpenseNote because WalletId does not match: %s"
              .formatted(category, walletId)
      );
    }
    ExpenseNote expenseNote = new ExpenseNote();
    expenseNote.walletId = walletId;
    expenseNote.whoDid = whoDid;
    expenseNote.date = date;
    expenseNote.amount = amount;
    expenseNote.comment = Objects.requireNonNullElse(comment, "");
    expenseNote.id = id;
    return expenseNote;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof ExpenseNote expenseNote) {
      return id != null && id.equals(expenseNote.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  public static class InvalidExpenseNoteException extends Exception {

    public InvalidExpenseNoteException(String message) {
      super(message);
    }
  }

  public static class InvalidExpenseNoteCategoryException extends InvalidExpenseNoteException {

    public InvalidExpenseNoteCategoryException(String message) {
      super(message);
    }
  }


}
