package com.plyushkin.repository;

import com.plyushkin.domain.budget.BudgetRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BudgetRecordRepository<T extends BudgetRecord<T>> extends
        JpaRepository<BudgetRecord<T>, UUID> {

    @Query("FROM #{#clazz.name}")
    List<T> findAll(Class<T> clazz);

    @Query("FROM #{#clazz.name}")
    Page<T> findAll(Class<T> clazz, Pageable pageable);
}
