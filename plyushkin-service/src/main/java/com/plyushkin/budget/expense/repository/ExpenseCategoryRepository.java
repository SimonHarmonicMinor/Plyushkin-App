package com.plyushkin.budget.expense.repository;

import com.plyushkin.budget.AbstractCategoryRepository;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.wallet.WalletId;

public interface ExpenseCategoryRepository extends AbstractCategoryRepository<ExpenseCategory, ExpenseCategoryNumber> {
    default ExpenseCategoryNumber nextNumber(WalletId walletId) {
        return findMaxNumberPerWalletId(walletId)
                .map(ExpenseCategoryNumber::increment)
                .orElse(ExpenseCategoryNumber.createOne());
    }
}
