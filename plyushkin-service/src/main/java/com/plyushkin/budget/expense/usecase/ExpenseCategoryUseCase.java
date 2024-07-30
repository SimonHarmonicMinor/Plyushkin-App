package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseCategoryNumber;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.UpdateCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.UpdateExpenseCategoryException;
import com.plyushkin.util.WriteTransactional;
import com.plyushkin.wallet.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryUseCase {

    private final ExpenseCategoryRepository repository;

    @WriteTransactional
    public ExpenseCategory createCategory(CreateCategoryCommand command) throws CreateCategoryException {
        repository.lockByWalletId(command.walletId());
        if (repository.existsByNameAndWalletId(command.walletId(), command.name())) {
            throw new CreateCategoryException.NonUniqueNamePerWalletId(
                    "Name %s is already taken for WalletId %s"
                            .formatted(command.name(), command.walletId()),
                    command.name()
            );
        }
        ExpenseCategoryNumber expenseCategoryNumber =
                repository.findMaxNumberPerWalletId(command.walletId())
                        .orElse(ExpenseCategoryNumber.createOne());

        return repository.save(ExpenseCategory.create(
                expenseCategoryNumber,
                command.name(),
                command.walletId(),
                command.whoCreated()
        ));
    }

    @WriteTransactional
    public ExpenseCategory update(WalletId walletId,
                                  ExpenseCategoryNumber number,
                                  UpdateCommand command)
            throws UpdateExpenseCategoryException {
        repository.lockByWalletId(walletId);
        ExpenseCategory root = repository.findByWalletIdAndNumber(walletId, number)
                .orElseThrow(() -> new UpdateExpenseCategoryException.ChangeParent.RootNotFound(
                        "Cannot find root category by WalletId %s and number %s"
                                .formatted(walletId, number)
                ));
        ExpenseCategory newParent = null;
        if (command.parentCategoryNumber() != null) {
            newParent = repository.findByWalletIdAndNumber(walletId, command.parentCategoryNumber())
                    .orElseThrow(() -> new UpdateExpenseCategoryException.ChangeParent.ParentNotFound(
                            "Cannot find child category by WalletId %s and number %s"
                                    .formatted(walletId, command.parentCategoryNumber())
                    ));
        }

        try {
            root.update(command.name(), newParent);
        } catch (AbstractCategory.UpdateCategoryException e) {
            switch (e) {
                case AbstractCategory.UpdateCategoryException.ChangeParent.MismatchedWalletId err ->
                        throw new UpdateExpenseCategoryException.ChangeParent.MismatchedWalletId(
                                "Mismatched WalletId " + walletId, err
                        );
                case AbstractCategory.UpdateCategoryException.ChangeParent.ParentEqualsToRoot err ->
                        throw new UpdateExpenseCategoryException.ChangeParent.ParentEqualsToRoot(
                                "Parent equals to root", err
                        );
            }
        }
        return repository.save(root);
    }
}
