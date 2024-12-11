package com.plyushkin.repository;

import com.plyushkin.domain.budget.BudgetRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BudgetRecordRepository extends
        JpaRepository<BudgetRecord, UUID>, CustomBudgetRecordRepository {
}
