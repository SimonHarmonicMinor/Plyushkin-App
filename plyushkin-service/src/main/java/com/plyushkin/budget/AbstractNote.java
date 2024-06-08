package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public class AbstractNote<I, CI, C extends AbstractCategory<CI, C>, T extends AbstractNote<I, CI, C, T>> extends
    AbstractAggregateRoot<T> {

  @Id
  @Getter(PRIVATE)
  @ToString.Include
  private Long pk;

  @Embedded
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
  private Money amount;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "category_id")
  private C category;

  @ToString.Include
  private String comment;

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
      return id != null && id.equals(abstractNote.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  private void validateCategory(C category) throws InvalidNoteCategoryException {
    if (category.getChildren().size() != 0) {
      throw new InvalidNoteCategoryException(
          "Category '%s' cannot be assigned because it has children: %s"
              .formatted(category, category.getChildren())
      );
    }
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