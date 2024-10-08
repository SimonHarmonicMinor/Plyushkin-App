package com.plyushkin.shared.budget.repository;

import com.plyushkin.shared.budget.domain.AbstractCategory;
import com.plyushkin.shared.budget.domain.AbstractNumber;
import com.plyushkin.shared.budget.domain.AbstractRecord;
import com.plyushkin.shared.WalletId;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.Optional;

import static jakarta.persistence.LockModeType.PESSIMISTIC_WRITE;

@NoRepositoryBean
public interface AbstractRecordRepository<
        C extends AbstractCategory<C>,
        R extends AbstractRecord<C, R>,
        N extends AbstractNumber<N>
        >
        extends JpaRepository<R, Long>, JpaSpecificationExecutor<R> {

    default Specification<R> pageSpecification(WalletId walletId, @Nullable LocalDate from, @Nullable LocalDate to) {
        return (root, query, cb) -> {
            root.fetch("category");
            return cb.and(
                    cb.equal(root.get("walletId"), walletId),
                    from != null ? cb.greaterThanOrEqualTo(root.get("date"), from) : cb.conjunction(),
                    to != null ? cb.lessThanOrEqualTo(root.get("date"), to) : cb.conjunction()
            );
        };
    }

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
