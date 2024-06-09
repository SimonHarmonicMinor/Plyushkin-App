package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static java.util.Optional.ofNullable;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
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
public abstract class AbstractCategory<I extends Serializable, T extends AbstractCategory<I, T>> extends
    AbstractAggregateRoot<T> {

  @EmbeddedId
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

  public void addChildCategory(T newChild) throws AddChildCategoryException {
    if (!newChild.walletId.equals(walletId)) {
      throw new AddChildCategoryException(
          "Cannot add child %s because WalletId is not %s"
              .formatted(newChild, walletId)
      );
    }
    newChild.parent = (T) this;
    this.children.add(newChild);
  }

  public void update(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o instanceof AbstractCategory abstractCategory) {
      return Objects.equals(id, abstractCategory.id);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  public static class ChangeParentCategoryException extends Exception {

    public ChangeParentCategoryException(String message) {
      super(message);
    }
  }

  public static class AddChildCategoryException extends Exception {

    public AddChildCategoryException(String message) {
      super(message);
    }
  }
}
