package com.plyushkin.budget.expense.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface ExpenseCategoryRepository extends EntityGraphJpaRepository<ExpenseCategory, Long> {

    default ExpenseCategoryNumber nextNumber(WalletId walletId) {
        return findMaxNumberPerWalletId(walletId)
                .map(ExpenseCategoryNumber::increment)
                .orElse(ExpenseCategoryNumber.createOne());
    }

    @Query("SELECT MAX(c.number) FROM ExpenseCategory c WHERE c.walletId = :walletId")
    Optional<ExpenseCategoryNumber> findMaxNumberPerWalletId(WalletId walletId);

    @Query("SELECT COUNT(c) > 0 FROM ExpenseCategory c WHERE c.walletId = :walletId AND c.name = :name")
    boolean existsByNameAndWalletId(WalletId walletId, String name);

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    Optional<ExpenseCategory> findByWalletIdAndNumber(WalletId walletId, ExpenseCategoryNumber number);

    Optional<ExpenseCategory> findByWalletIdAndNumber(
            WalletId walletId,
            ExpenseCategoryNumber number,
            EntityGraph entityGraph
    );

    List<ExpenseCategory> findAllByWalletId(WalletId walletId, EntityGraph entityGraph);
}
