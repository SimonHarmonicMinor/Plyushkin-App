package com.plyushkin.budget;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@NoRepositoryBean
public interface AbstractCategoryRepository<T extends AbstractCategory<T>, N extends CategoryNumber<N>> extends EntityGraphJpaRepository<T, Long> {

    @Query("SELECT MAX(c.number) FROM #{#entityName} c WHERE c.walletId = :walletId GROUP BY c.walletId")
    Optional<N> findMaxNumberPerWalletId(WalletId walletId);

    @Query("SELECT COUNT(c) > 0 FROM #{#entityName} c WHERE c.walletId = :walletId AND c.name = :name")
    boolean existsByNameAndWalletId(WalletId walletId, String name);

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    Optional<T> findByWalletIdAndNumber(WalletId walletId, ExpenseCategoryNumber number);

    Optional<T> findByWalletIdAndNumber(
            WalletId walletId,
            ExpenseCategoryNumber number,
            EntityGraph entityGraph
    );

    List<T> findAllByWalletId(WalletId walletId, EntityGraph entityGraph);
}
