package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class AbstractNote<I extends Serializable, CI extends Serializable, C extends AbstractCategory<CI, C>, T extends AbstractNote<I, CI, C, T>>
    extends AbstractAggregateRoot<T> {

  @EmbeddedId
  @ToString.Include
  private I id;

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
  @AttributeOverride(name = "value", column = @Column(name = "amount"))
  private Money amount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private C category;

  @ToString.Include
  private String comment;

  @SuppressFBWarnings("CT_CONSTRUCTOR_THROW")
  protected AbstractNote(
      I id,
      WalletId walletId,
      UserId whoDid,
      LocalDate date,
      Money amount,
      C category,
      String comment
  ) throws InvalidNoteException {
    validateCategory(category);
    this.id = id;
    this.walletId = walletId;
    this.whoDid = whoDid;
    this.date = date;
    this.amount = amount;
    this.category = category;
    this.comment = comment;
  }

  public void update(
      LocalDate date,
      Money amount,
      C category,
      String comment
  ) throws InvalidNoteCategoryException {
    validateCategory(category);
    this.date = date;
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
      return id.equals(abstractNote.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  private void validateCategory(C category) throws InvalidNoteCategoryException {
    if (!category.getWalletId().equals(walletId)) {
      throw new InvalidNoteCategoryException(
          "Category '%s' cannot be assigned because WalletId does not match: %s"
              .formatted(category, walletId)
      );
    }
  }

  public static class InvalidNoteException extends Exception {

    public InvalidNoteException(String message) {
      super(message);
    }
  }

  public static class InvalidNoteCategoryException extends
      InvalidNoteException {

    public InvalidNoteCategoryException(String message) {
      super(message);
    }
  }
}
