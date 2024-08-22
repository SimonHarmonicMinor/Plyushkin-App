package com.plyushkin.wallet.repository;

import com.plyushkin.shared.UserId;
import com.plyushkin.wallet.domain.Wallet;
import com.plyushkin.shared.WalletId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletRepository extends JpaRepository<Wallet, WalletId> {
    List<Wallet> findAllByCreatedBy(UserId createdBy);
}
