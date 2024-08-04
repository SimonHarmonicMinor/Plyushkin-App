package com.plyushkin.budget.expense.repository;

import com.cosium.spring.data.jpa.entity.graph.domain2.EntityGraph;
import com.plyushkin.budget.Currency;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseNumber;
import com.plyushkin.budget.expense.ExpenseRecord;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

public interface ExpenseRecordRepository extends JpaRepository<ExpenseRecord, Long> {
    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    boolean existsByCategory(ExpenseCategory category);

    default ExpenseNumber nextNumber(WalletId walletId) {
        return findMaxNumberPerWalletId(walletId)
                .map(ExpenseNumber::increment)
                .orElse(ExpenseNumber.createOne());
    }

    @Query("SELECT MAX(c.number) FROM ExpenseRecord c WHERE c.walletId = :walletId GROUP BY c.walletId")
    Optional<ExpenseNumber> findMaxNumberPerWalletId(WalletId walletId);

    Optional<ExpenseRecord> findByWalletIdAndNumber(WalletId walletId, ExpenseNumber number);

    Page<ExpenseRecord> findAllByWalletId(WalletId walletId, Pageable pageable, EntityGraph entityGraph);

    Page<ExpenseRecord> findAllByWalletIdAndCurrencyIn(WalletId walletId, Collection<Currency> currency, Pageable pageable, EntityGraph entityGraph);
}
