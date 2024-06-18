package com.plyushkin.budget;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.plyushkin.user.UserId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
import lombok.experimental.StandardException;
import org.springframework.data.domain.AbstractAggregateRoot;

@MappedSuperclass
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = PROTECTED)
@Getter
public abstract class AbstractCategory<I extends Serializable, T extends AbstractCategory<I, T>> extends
    AbstractAggregateRoot<T> {

  @Embedded
  @ToString.Include
  protected I number;

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

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id")
  @Nullable
  protected T parent;

  @OneToMany(mappedBy = "parent")
  protected Set<T> children;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter(PRIVATE)
  private Long pk;

  public void changeParent(@Nullable T newParent) throws ChangeParentCategoryException {
    if (newParent != null && !newParent.walletId.equals(walletId)) {
      throw new ChangeParentCategoryException.MismatchedWalletId(
          "Cannot set parent %s because WalletId is not %s"
              .formatted(newParent, walletId)
      );
    }
    if (Objects.equals(newParent, this)) {
      throw new ChangeParentCategoryException.ParentEqualsToRoot(
          "Category cannot be a parent to itself"
      );
    }
    this.parent = newParent;
  }

  @SuppressWarnings("unchecked")
  public void addChildCategory(T newChild) throws AddChildCategoryException {
    if (!newChild.walletId.equals(walletId)) {
      throw new AddChildCategoryException.MismatchedWalletId(
          "Cannot add child %s because WalletId is not %s"
              .formatted(newChild, walletId)
      );
    }
    if (Objects.equals(newChild, this)) {
      throw new AddChildCategoryException.ChildEqualsToRoot(
          "Category cannot be a child to itself"
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
      return pk != null && Objects.equals(pk, abstractCategory.pk);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @StandardException
  public abstract static sealed class ChangeParentCategoryException extends Exception {

    @StandardException
    public static final class MismatchedWalletId extends ChangeParentCategoryException {

    }

    @StandardException
    public static final class ParentEqualsToRoot extends ChangeParentCategoryException {

    }
  }

  @StandardException
  public abstract static sealed class AddChildCategoryException extends Exception {

    @StandardException
    public static final class MismatchedWalletId extends AddChildCategoryException {

    }

    @StandardException
    public static final class ChildEqualsToRoot extends AddChildCategoryException {

    }
  }
}
