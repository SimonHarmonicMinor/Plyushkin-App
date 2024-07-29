package com.plyushkin.wallet.repository;

import com.plyushkin.wallet.Wallet;
import com.plyushkin.wallet.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, WalletId> {
}
