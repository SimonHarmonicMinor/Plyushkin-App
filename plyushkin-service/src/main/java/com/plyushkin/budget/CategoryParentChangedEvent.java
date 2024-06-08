package com.plyushkin.budget;

import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

public record CategoryParentChangedEvent<I>(WalletId walletId,
                                            I categoryId,
                                            @Nullable I newParentId,
                                            @Nullable I oldParentId) implements
    ResolvableTypeProvider {

  @Override
  public ResolvableType getResolvableType() {
    return ResolvableType.forClassWithGenerics(
        getClass(),
        ResolvableType.forInstance(categoryId)
    );
  }
}
