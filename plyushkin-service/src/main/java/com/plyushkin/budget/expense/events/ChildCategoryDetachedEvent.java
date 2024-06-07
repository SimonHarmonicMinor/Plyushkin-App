package com.plyushkin.budget.expense.events;

import com.plyushkin.budget.expense.ExpenseNoteCategoryId;
import com.plyushkin.wallet.WalletId;

public record ChildCategoryDetachedEvent(WalletId walletId,
                                         ExpenseNoteCategoryId parentCategoryId,
                                         ExpenseNoteCategoryId childCategoryId) {

}
