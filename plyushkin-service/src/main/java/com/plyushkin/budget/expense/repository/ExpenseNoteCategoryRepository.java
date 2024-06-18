package com.plyushkin.budget.expense.repository;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.wallet.WalletId;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface ExpenseNoteCategoryRepository extends JpaRepository<ExpenseNoteCategory, Long> {

    @Query("SELECT MAX(c.number) FROM ExpenseNoteCategory c WHERE c.walletId = :walletId")
    Optional<ExpenseNoteCategoryNumber> findMaxNumberPerWalletId(WalletId walletId);

    @Query("SELECT COUNT(c) > 0 FROM ExpenseNoteCategory c WHERE c.walletId = :walletId AND c.name = :name")
    boolean existsByNameAndWalletId(WalletId walletId, String name);

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    Optional<ExpenseNoteCategory> findByWalletIdAndNumber(WalletId walletId, ExpenseNoteCategoryNumber number);
}
