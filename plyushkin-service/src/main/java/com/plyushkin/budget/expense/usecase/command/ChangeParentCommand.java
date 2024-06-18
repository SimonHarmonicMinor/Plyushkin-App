package com.plyushkin.budget.expense.usecase.command;

import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;

public record ChangeParentCommand(WalletId walletId,
                                  ExpenseNoteCategoryNumber rootCategoryNumber,
                                  String name,
                                  @Nullable ExpenseNoteCategoryNumber parentCategoryNumber) {

}
