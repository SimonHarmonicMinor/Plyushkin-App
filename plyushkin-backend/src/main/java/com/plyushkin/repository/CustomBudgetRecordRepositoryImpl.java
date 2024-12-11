package com.plyushkin.repository;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.budget.Wallet;
import jakarta.annotation.Nullable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
class CustomBudgetRecordRepositoryImpl implements CustomBudgetRecordRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public <T extends BudgetRecord> List<T> findAll(Class<T> clazz,
                                                    Wallet wallet,
                                                    @Nullable LocalDate from,
                                                    @Nullable LocalDate to) {
        return em.createQuery(
                        """
                                SELECT b FROM %s b
                                WHERE b.wallet = :wallet
                                AND (:from IS NULL OR b.date >= :from)
                                AND (:to IS NULL OR b.date <= :to)
                                ORDER BY b.date, b.id"""
                                .formatted(clazz.getName()),
                        clazz
                )
                .setParameter("wallet", wallet)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
