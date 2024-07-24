package com.plyushkin.budget.expense.usecase.command;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.wallet.WalletId;

public record AddChildCommand(WalletId walletId,
                              ExpenseCategoryNumber rootCategoryNumber,
                              ExpenseCategoryNumber childCategoryNumber) {

}
