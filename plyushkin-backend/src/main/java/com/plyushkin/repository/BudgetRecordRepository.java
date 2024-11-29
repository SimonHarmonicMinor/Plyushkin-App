package com.plyushkin.repository;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.domain.value.ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRecordRepository<T extends BudgetRecord<T>> extends JpaRepository<BudgetRecord<T>, ID<T>> {
}
