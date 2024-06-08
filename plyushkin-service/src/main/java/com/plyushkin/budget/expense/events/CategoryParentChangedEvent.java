package com.plyushkin.budget.expense.events;

import com.plyushkin.budget.expense.ExpenseNoteCategoryId;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;

public record CategoryParentChangedEvent(WalletId walletId,
                                         ExpenseNoteCategoryId categoryId,
                                         @Nullable ExpenseNoteCategoryId newParentId,
                                         @Nullable ExpenseNoteCategoryId oldParentId) {

}
