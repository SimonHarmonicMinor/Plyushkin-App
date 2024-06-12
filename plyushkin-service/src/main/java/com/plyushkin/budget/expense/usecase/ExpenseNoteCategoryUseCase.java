package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.AbstractCategory.AddChildCategoryException;
import com.plyushkin.budget.AbstractCategory.AddChildCategoryException.ChildEqualsToRoot;
import com.plyushkin.budget.AbstractCategory.AddChildCategoryException.MismatchedWalletId;
import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.repository.ExpenseNoteCategoryRepository;
import com.plyushkin.budget.expense.usecase.command.AddChildExpenseNoteCategoryCommand;
import com.plyushkin.budget.expense.usecase.command.CreateExpenseNoteCategoryCommand;
import com.plyushkin.budget.expense.usecase.exception.CannotAddChildExpenseNoteCategoryException;
import com.plyushkin.budget.expense.usecase.exception.CannotAddChildExpenseNoteCategoryException.ChildCategoryNotFound;
import com.plyushkin.budget.expense.usecase.exception.CannotAddChildExpenseNoteCategoryException.RootCategoryNotFound;
import com.plyushkin.budget.expense.usecase.exception.CannotCreateExpenseNoteCategoryException;
import com.plyushkin.util.WriteTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseNoteCategoryUseCase {

  private final ExpenseNoteCategoryRepository repository;

  @WriteTransactional
  public ExpenseNoteCategoryNumber createCategory(CreateExpenseNoteCategoryCommand command)
      throws CannotCreateExpenseNoteCategoryException {
    repository.lockByWalletId(command.walletId());
    if (repository.existsByNameAndWalletId(command.walletId(), command.name())) {
      throw new CannotCreateExpenseNoteCategoryException.NonUniqueNamePerWalletId(
          "Name %s is already taken for WalletId %s"
              .formatted(command.name(), command.walletId())
      );
    }
    ExpenseNoteCategoryNumber expenseNoteCategoryNumber =
        repository.findMaxNumberPerWalletId(
            command.walletId()
        ).orElse(ExpenseNoteCategoryNumber.createOne());

    return repository.save(
        ExpenseNoteCategory.create(
            expenseNoteCategoryNumber,
            command.name(),
            command.walletId(),
            command.whoCreated()
        )
    ).getNumber();
  }

  @WriteTransactional
  public void addChildCategory(AddChildExpenseNoteCategoryCommand command)
      throws CannotAddChildExpenseNoteCategoryException {
    repository.lockByWalletId(command.walletId());
    ExpenseNoteCategory root = repository.findByWalletIdAndNumber(
            command.walletId(),
            command.rootCategoryNumber()
        )
        .orElseThrow(() -> new RootCategoryNotFound(
            "Cannot find root category by WalletId %s and number %s"
                .formatted(command.walletId(), command.rootCategoryNumber())
        ));
    ExpenseNoteCategory child = repository.findByWalletIdAndNumber(
        command.walletId(),
        command.childCategoryNumber()
    ).orElse(() -> new ChildCategoryNotFound(
        "Cannot find child category by WalletId %s and number %s"
            .formatted(command.walletId(), command.rootCategoryNumber())
    ));
    try {
      root.addChildCategory(child);
    } catch (AddChildCategoryException e) {
      switch (e) {
        case MismatchedWalletId err -> throw new CannotAddChildExpenseNoteCategoryException.MismatchedWalletId(
            "Mismatched WalletId " + command.walletId(), err
        )
        case ChildEqualsToRoot err -> throw new CannotAddChildExpenseNoteCategoryException.ChildEqualsToRoot(
            "Child equals to root", err
        )
      }
    }
    repository.save(root);
  }
}
