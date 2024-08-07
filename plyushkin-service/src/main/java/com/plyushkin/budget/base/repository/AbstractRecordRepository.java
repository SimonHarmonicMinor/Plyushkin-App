package com.plyushkin.budget.base.repository;

import com.plyushkin.budget.base.AbstractCategory;
import com.plyushkin.budget.base.AbstractRecord;
import com.plyushkin.budget.base.AbstractNumber;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@NoRepositoryBean
public interface AbstractRecordRepository<
        C extends AbstractCategory<C>,
        R extends AbstractRecord<C, R>,
        N extends AbstractNumber<N>
        >
        extends JpaRepository<R, Long>, JpaSpecificationExecutor<R> {

    N initialNumber();

    @Query("SELECT w.id FROM Wallet w WHERE w.id = :walletId")
    @Lock(PESSIMISTIC_WRITE)
    void lockByWalletId(WalletId walletId);

    boolean existsByCategory(C category);

    default N nextNumber(WalletId walletId) {
        return findMaxNumberPerWalletId(walletId)
                .map(AbstractNumber::increment)
                .orElse(initialNumber());
    }

    @Query("SELECT MAX(c.number) FROM #{#entityName} c WHERE c.walletId = :walletId GROUP BY c.walletId")
    Optional<N> findMaxNumberPerWalletId(WalletId walletId);

    Optional<R> findByWalletIdAndNumber(WalletId walletId, N number);
}
