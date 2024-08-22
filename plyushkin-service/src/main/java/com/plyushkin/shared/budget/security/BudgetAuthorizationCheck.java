package com.plyushkin.shared.budget.security;

import com.plyushkin.shared.CurrentUserIdProvider;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WalletId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("BudgetAuth")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
class BudgetAuthorizationCheck {
    private final CurrentUserIdProvider currentUserIdProvider;
    private final EntityManager em;

    public boolean hasAccessForWalletView(WalletId walletId) {
        return hasAccessForWalletUpdate(walletId);
    }

    public boolean hasAccessForWalletUpdate(WalletId walletId) {
        final var currentUserId = currentUserIdProvider.get();
        try {

            final var userId = em.createQuery("SELECT w.createdBy FROM Wallet w WHERE w.id = :walletId", UserId.class)
                    .setParameter("walletId", walletId)
                    .getSingleResult();
            return userId.equals(currentUserId);
        } catch (NoResultException e) {
            log.warn("Wallet is not found by id={}", walletId, e);
            return false;
        }
    }
}
