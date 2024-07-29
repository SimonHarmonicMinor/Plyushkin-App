package com.plyushkin.budget;

import com.plyushkin.user.service.CurrentUserIdProvider;
import com.plyushkin.wallet.WalletId;
import com.plyushkin.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BudgetAuth")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BudgetAuthorizationCheck {
    private final CurrentUserIdProvider currentUserIdProvider;
    private final WalletRepository walletRepository;

    public boolean hasAccessForWalletView(WalletId walletId) {
        return hasAccessForWalletUpdate(walletId);
    }

    public boolean hasAccessForWalletUpdate(WalletId walletId) {
        final var currentUserId = currentUserIdProvider.get();
        return walletRepository.findById(walletId)
                .map(wallet -> wallet.getCreatedBy().equals(currentUserId))
                .orElseGet(() -> {
                    log.info("Wallet is not found by id={}", walletId);
                    return false;
                });
    }
}
