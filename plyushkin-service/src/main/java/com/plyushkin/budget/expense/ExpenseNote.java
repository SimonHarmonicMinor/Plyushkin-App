package com.plyushkin.budget.expense;

import static jakarta.persistence.GenerationType.IDENTITY;
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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name = "expense_note")
@NoArgsConstructor(access = PROTECTED)
@Getter(PACKAGE)
public class ExpenseNote extends AbstractAggregateRoot<ExpenseNote> {
  @Id
  @Getter(PRIVATE)
  private Long pk;

  @Embedded
  private ExpenseNoteId id;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "wallet_id"))
  )
  private WalletId walletId;

  @Embedded
  @AttributeOverrides(
      @AttributeOverride(name = "value", column = @Column(name = "who_did_id"))
  )
  private UserId whoDid;

  private LocalDate date;

  @Embedded
  private Money amount;

  private String comment;

  public static ExpenseNote create() throws InvalidExpenseNoteException {

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
