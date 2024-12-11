package com.plyushkin.repository;

import com.plyushkin.domain.budget.Wallet;
import com.plyushkin.domain.value.ID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, ID<Wallet>> {
}
