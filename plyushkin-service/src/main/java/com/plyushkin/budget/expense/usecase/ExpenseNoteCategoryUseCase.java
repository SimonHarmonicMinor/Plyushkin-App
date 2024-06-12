package com.plyushkin.budget.expense.usecase;

import com.plyushkin.budget.AbstractCategory.AddChildCategoryException;
import com.plyushkin.budget.AbstractCategory.ChangeParentCategoryException;
import com.plyushkin.budget.expense.ExpenseNoteCategory;
import com.plyushkin.budget.expense.ExpenseNoteCategoryNumber;
import com.plyushkin.budget.expense.repository.ExpenseNoteCategoryRepository;
import com.plyushkin.budget.expense.usecase.command.AddChildCommand;
import com.plyushkin.budget.expense.usecase.command.ChangeParentCommand;
import com.plyushkin.budget.expense.usecase.command.CreateCategoryCommand;
import com.plyushkin.budget.expense.usecase.exception.AddChildException;
import com.plyushkin.budget.expense.usecase.exception.ChangeParentException;
import com.plyushkin.budget.expense.usecase.exception.CreateCategoryException;
import com.plyushkin.util.WriteTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseNoteCategoryUseCase {

  private final ExpenseNoteCategoryRepository repository;

  @WriteTransactional
  public ExpenseNoteCategoryNumber createCategory(CreateCategoryCommand command)
      throws CreateCategoryException {
    repository.lockByWalletId(command.walletId());
    if (repository.existsByNameAndWalletId(command.walletId(), command.name())) {
      throw new CreateCategoryException.NonUniqueNamePerWalletId(
          "Name %s is already taken for WalletId %s"
              .formatted(command.name(), command.walletId()),
          command.name()
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
  public void addChildCategory(AddChildCommand command)
      throws AddChildException {
    repository.lockByWalletId(command.walletId());
    ExpenseNoteCategory root = repository.findByWalletIdAndNumber(
            command.walletId(),
            command.rootCategoryNumber()
        )
        .orElseThrow(() -> new AddChildException.RootCategoryNotFound(
            "Cannot find root category by WalletId %s and number %s"
                .formatted(command.walletId(), command.rootCategoryNumber())
        ));
    ExpenseNoteCategory child = repository.findByWalletIdAndNumber(
        command.walletId(),
        command.childCategoryNumber()
    ).orElseThrow(() -> new AddChildException.ChildCategoryNotFound(
        "Cannot find child category by WalletId %s and number %s"
            .formatted(command.walletId(), command.rootCategoryNumber())
    ));
    try {
      root.addChildCategory(child);
    } catch (AddChildCategoryException e) {
      switch (e) {
        case AddChildCategoryException.MismatchedWalletId err -> throw new AddChildException.MismatchedWalletId(
            "Mismatched WalletId " + command.walletId(), err
        );
        case AddChildCategoryException.ChildEqualsToRoot err -> throw new AddChildException.ChildEqualsToRoot(
            "Child equals to root", err
        );
        default -> throw new IllegalStateException("Unknown value " + e, e);
      }
    }
    repository.save(root);
  }

  @WriteTransactional
  public void changeParentCategory(ChangeParentCommand command)
      throws ChangeParentException {
    repository.lockByWalletId(command.walletId());
    ExpenseNoteCategory root = repository.findByWalletIdAndNumber(
            command.walletId(),
            command.rootCategoryNumber()
        )
        .orElseThrow(() -> new ChangeParentException.RootNotFound(
            "Cannot find root category by WalletId %s and number %s"
                .formatted(command.walletId(), command.rootCategoryNumber())
        ));
    ExpenseNoteCategory newParent = command.parentCategoryNumber() == null
        ? null
        : repository.findByWalletIdAndNumber(command.walletId(), command.parentCategoryNumber())
            .orElseThrow(() -> new ChangeParentException.ParentNotFound(
                "Cannot find child category by WalletId %s and number %s"
                    .formatted(command.walletId(), command.parentCategoryNumber())
            ));
    try {
      root.changeParent(newParent);
    } catch (ChangeParentCategoryException e) {
      switch (e) {
        case ChangeParentCategoryException.MismatchedWalletId err -> throw new ChangeParentException.MismatchedWalletId(
            "Mismatched WalletId " + command.walletId(), err
        );
        case ChangeParentCategoryException.ParentEqualsToRoot err -> throw new ChangeParentException.ParentEqualsToRoot(
            "Parent equals to root", err
        );
        default -> throw new IllegalStateException("Unknown value " + e, e);
      }
    }
    repository.save(root);
  }
}
