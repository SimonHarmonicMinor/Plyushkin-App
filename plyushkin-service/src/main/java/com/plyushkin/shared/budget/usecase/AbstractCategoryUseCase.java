package com.plyushkin.shared.budget.usecase;

import com.plyushkin.shared.budget.domain.AbstractCategory;
import com.plyushkin.shared.budget.domain.AbstractNumber;
import com.plyushkin.shared.budget.repository.AbstractCategoryRepository;
import com.plyushkin.shared.budget.repository.AbstractRecordRepository;
import com.plyushkin.shared.budget.usecase.command.CreateCategoryCommand;
import com.plyushkin.shared.budget.usecase.command.UpdateCategoryCommand;
import com.plyushkin.shared.budget.usecase.exception.CategoryNotFoundException;
import com.plyushkin.shared.budget.usecase.exception.CreateCategoryException;
import com.plyushkin.shared.budget.usecase.exception.DeleteCategoryException;
import com.plyushkin.shared.budget.usecase.exception.UpdateCategoryUseCaseException;
import com.plyushkin.shared.WriteTransactional;
import com.plyushkin.shared.WalletId;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public abstract class AbstractCategoryUseCase<
        C extends AbstractCategory<C>,
        N extends AbstractNumber<N>,
        R extends AbstractCategoryRepository<C, N>,
        RR extends AbstractRecordRepository<C, ?, ?>
        > {
    private final R repository;
    private final RR recordRepository;
    private final BiFunction<N, CreateCategoryCommand, C> createCategoryFn;

    @WriteTransactional
    public C createCategory(CreateCategoryCommand command) throws CreateCategoryException {
        repository.lockByWalletId(command.walletId());
        if (repository.existsByNameAndWalletId(command.walletId(), command.name())) {
            throw new CreateCategoryException.NonUniqueNamePerWalletId(
                    "Name %s is already taken for WalletId %s"
                            .formatted(command.name(), command.walletId()),
                    command.name()
            );
        }
        N expenseCategoryNumber = repository.nextNumber(command.walletId());
        return repository.save(createCategoryFn.apply(expenseCategoryNumber, command));
    }

    @WriteTransactional
    public C updateCategory(WalletId walletId,
                            N number,
                            UpdateCategoryCommand<N> command) throws UpdateCategoryUseCaseException {
        repository.lockByWalletId(walletId);
        C root = repository.findByWalletIdAndNumber(walletId, number)
                .orElseThrow(() -> new UpdateCategoryUseCaseException.ChangeParent.RootNotFound(
                        "Cannot find root category by WalletId %s and number %s"
                                .formatted(walletId, number)
                ));

        C newParent = null;
        if (command.parentCategoryNumber() != null) {
            newParent = repository.findByWalletIdAndNumber(walletId, command.parentCategoryNumber())
                    .orElseThrow(() -> new UpdateCategoryUseCaseException.ChangeParent.ParentNotFound(
                            "Cannot find child category by WalletId %s and number %s"
                                    .formatted(walletId, command.parentCategoryNumber())
                    ));
        }

        try {
            root.update(command.name(), newParent);
        } catch (AbstractCategory.UpdateCategoryException e) {
            switch (e) {
                case AbstractCategory.UpdateCategoryException.ChangeParent.MismatchedWalletId err ->
                        throw new UpdateCategoryUseCaseException.ChangeParent.MismatchedWalletId(
                                "Mismatched WalletId " + walletId, err
                        );
                case AbstractCategory.UpdateCategoryException.ChangeParent.ParentEqualsToRoot err ->
                        throw new UpdateCategoryUseCaseException.ChangeParent.ParentEqualsToRoot(
                                "Parent equals to root", err
                        );
            }
        }
        return repository.save(root);
    }

    @WriteTransactional
    public void deleteCategory(WalletId walletId, N number) throws DeleteCategoryException {
        repository.lockByWalletId(walletId);
        final var category = repository.findByWalletIdAndNumber(walletId, number).orElse(null);
        if (category == null) {
            throw new DeleteCategoryException.NotFound(
                    "Cannot find category by WalletId=%s and number=%s"
                            .formatted(walletId, number)
            );
        }
        if (recordRepository.existsByCategory(category)) {
            throw new DeleteCategoryException.ReferencedByExpenseRecord(
                    "Cannot delete category because it's references by expense record(s). WalletId=%s, number=%s"
                            .formatted(walletId, number)
            );
        }
        repository.delete(category);
    }

    public List<C> listCategories(WalletId walletId) {
        return repository.findAllByWalletId(
                walletId
        );
    }

    public C getCategory(WalletId walletId, N number) throws CategoryNotFoundException {
        return repository.findByWalletIdAndNumber(
                walletId,
                number
        ).orElseThrow(() -> new CategoryNotFoundException(
                "Cannot find category by WalletId=%s and Number=%s"
                        .formatted(walletId, number)
        ));
    }
}
