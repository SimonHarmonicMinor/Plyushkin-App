package com.plyushkin.shared.budget.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.plyushkin.shared.budget.domain.AbstractNumber;
import com.plyushkin.shared.budget.domain.AbstractCategory;
import com.plyushkin.shared.WalletId;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@NoRepositoryBean
public interface AbstractCategoryRepository<
        T extends AbstractCategory<T>,
        N extends AbstractNumber<N>
        > extends EntityGraphJpaRepository<T, Long> {
    N initialNumber();

    default N nextNumber(WalletId walletId) {
        return findMaxNumberPerWalletId(walletId)
                .map(AbstractNumber::increment)
                .orElse(initialNumber());
    }

    @Query("SELECT MAX(c.number) FROM #{#entityName} c WHERE c.walletId = :walletId GROUP BY c.walletId")
    Optional<N> findMaxNumberPerWalletId(WalletId walletId);

    @Query("SELECT COUNT(c) > 0 FROM #{#entityName} c WHERE c.walletId = :walletId AND c.name = :name")
    boolean existsByNameAndWalletId(WalletId walletId, String name);

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    @EntityGraph(attributePaths = "parent")
    Optional<T> findByWalletIdAndNumber(WalletId walletId, N number);

    @EntityGraph(attributePaths = "parent")
    List<T> findAllByWalletId(WalletId walletId);
}
