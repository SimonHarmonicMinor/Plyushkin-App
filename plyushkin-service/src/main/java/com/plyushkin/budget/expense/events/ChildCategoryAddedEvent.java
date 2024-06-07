package com.plyushkin.budget.expense.events;

import com.plyushkin.budget.expense.ExpenseNoteCategoryId;
import com.plyushkin.wallet.WalletId;

public record ChildCategoryAddedEvent(WalletId walletId,
                                      ExpenseNoteCategoryId parentCategoryId,
                                      ExpenseNoteCategoryId childCategoryId) {

}
