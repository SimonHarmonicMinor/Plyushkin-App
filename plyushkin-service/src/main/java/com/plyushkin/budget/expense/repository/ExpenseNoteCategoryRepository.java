package com.plyushkin.budget.expense.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaRepository;
import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface ExpenseNoteCategoryRepository extends EntityGraphJpaRepository<ExpenseNoteCategory, Long> {

    @Query("SELECT MAX(c.number) FROM ExpenseNoteCategory c WHERE c.walletId = :walletId")
    Optional<ExpenseNoteCategoryNumber> findMaxNumberPerWalletId(WalletId walletId);

    @Query("SELECT COUNT(c) > 0 FROM ExpenseNoteCategory c WHERE c.walletId = :walletId AND c.name = :name")
    boolean existsByNameAndWalletId(WalletId walletId, String name);

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    Optional<ExpenseNoteCategory> findByWalletIdAndNumber(WalletId walletId, ExpenseNoteCategoryNumber number);

    Optional<ExpenseNoteCategory> findByWalletIdAndNumber(
            WalletId walletId,
            ExpenseNoteCategoryNumber number,
            EntityGraph entityGraph
    );

    List<ExpenseNoteCategory> findAllByWalletId(WalletId walletId, EntityGraph entityGraph);
}
