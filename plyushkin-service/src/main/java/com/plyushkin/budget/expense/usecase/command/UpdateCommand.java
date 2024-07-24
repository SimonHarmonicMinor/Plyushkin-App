package com.plyushkin.budget.expense.usecase.command;

import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.wallet.WalletId;
import jakarta.annotation.Nullable;

public record UpdateCommand(@Nullable String name,
                            @Nullable ExpenseCategoryNumber parentCategoryNumber) {

}
