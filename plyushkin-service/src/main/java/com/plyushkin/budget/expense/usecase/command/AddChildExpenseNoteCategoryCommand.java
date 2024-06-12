package com.plyushkin.budget.expense.usecase.command;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.wallet.WalletId;

public record AddChildExpenseNoteCategoryCommand(WalletId walletId,
                                                 ExpenseNoteCategoryNumber rootCategoryNumber,
                                                 ExpenseNoteCategoryNumber childCategoryNumber) {

}
