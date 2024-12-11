package com.plyushkin.service;

import com.plyushkin.domain.budget.BudgetRecord;
import com.plyushkin.repository.BudgetRecordRepository;
import com.plyushkin.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetService {
    private final BudgetRecordRepository repo;
    private final WalletRepository walletRepo;

    public <T extends BudgetRecord> List<T> getAll(Class<T> clazz,
                                                   GetBudgetRecordsCommand command) {
        return repo.findAll(
                clazz,
                walletRepo.getReferenceById(command.walletId()),
                command.from(),
                command.to()
        );
    }
}
