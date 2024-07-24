package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.AbstractCategory;
import com.plyushkin.budget.expense.ExpenseCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.repository.ExpenseCategoryRepository;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.UpdateCommand;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.budget.expense.usecase.exception.UpdateExpenseNoteCategoryException;
import com.plyushkin.util.WriteTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseCategoryUseCase {

    private final ExpenseCategoryRepository repository;

    @WriteTransactional
    public ExpenseNoteCategoryNumber createCategory(CreateCategoryCommand command) throws CreateCategoryException {
        repository.lockByWalletId(command.walletId());
        if (repository.existsByNameAndWalletId(command.walletId(), command.name())) {
            throw new CreateCategoryException.NonUniqueNamePerWalletId(
                    "Name %s is already taken for WalletId %s"
                            .formatted(command.name(), command.walletId()),
                    command.name()
            );
        }
        ExpenseNoteCategoryNumber expenseNoteCategoryNumber =
                repository.findMaxNumberPerWalletId(command.walletId())
                        .orElse(ExpenseNoteCategoryNumber.createOne());

        return repository.save(ExpenseCategory.create(
                expenseNoteCategoryNumber,
                command.name(),
                command.walletId(),
                command.whoCreated()
        )).getNumber();
    }

    @WriteTransactional
    public void update(UpdateCommand command) throws UpdateExpenseNoteCategoryException {
        repository.lockByWalletId(command.walletId());
        ExpenseCategory root = repository.findByWalletIdAndNumber(command.walletId(), command.rootCategoryNumber())
                .orElseThrow(() -> new UpdateExpenseNoteCategoryException.ChangeParent.RootNotFound(
                        "Cannot find root category by WalletId %s and number %s"
                                .formatted(command.walletId(), command.rootCategoryNumber())
                ));
        ExpenseCategory newParent =
                command.parentCategoryNumber() == null
                        ?
                        null
                        :
                        repository.findByWalletIdAndNumber(command.walletId(), command.parentCategoryNumber())
                                .orElseThrow(() -> new UpdateExpenseNoteCategoryException.ChangeParent.ParentNotFound(
                                        "Cannot find child category by WalletId %s and number %s"
                                                .formatted(command.walletId(), command.parentCategoryNumber())
                                ));
        try {
            root.update(command.name(), newParent);
        } catch (AbstractCategory.UpdateCategoryException e) {
            switch (e) {
                case AbstractCategory.UpdateCategoryException.ChangeParent.MismatchedWalletId err ->
                        throw new UpdateExpenseNoteCategoryException.ChangeParent.MismatchedWalletId(
                                "Mismatched WalletId " + command.walletId(), err
                        );
                case AbstractCategory.UpdateCategoryException.ChangeParent.ParentEqualsToRoot err ->
                        throw new UpdateExpenseNoteCategoryException.ChangeParent.ParentEqualsToRoot(
                                "Parent equals to root", err
                        );
            }
        }
        repository.save(root);
    }
}
