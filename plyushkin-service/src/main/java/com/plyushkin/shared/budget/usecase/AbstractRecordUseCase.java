package com.plyushkin.shared.budget.usecase;

import com.plyushkin.shared.budget.domain.Currency;
import com.plyushkin.shared.budget.domain.Money;
import com.plyushkin.shared.budget.domain.AbstractCategory;
import com.plyushkin.shared.budget.domain.AbstractNumber;
import com.plyushkin.shared.budget.domain.AbstractRecord;
import com.plyushkin.shared.budget.repository.AbstractCategoryRepository;
import com.plyushkin.shared.budget.repository.AbstractRecordRepository;
import com.plyushkin.shared.budget.usecase.command.CreateRecordCommand;
import com.plyushkin.shared.budget.usecase.command.ListRecordsCommand;
import com.plyushkin.shared.budget.usecase.command.UpdateRecordCommand;
import com.plyushkin.shared.budget.usecase.exception.CreateRecordException;
import com.plyushkin.shared.budget.usecase.exception.DeleteRecordException;
import com.plyushkin.shared.budget.usecase.exception.RecordNotFoundException;
import com.plyushkin.shared.budget.usecase.exception.UpdateRecordException;
import com.plyushkin.shared.UserId;
import com.plyushkin.shared.WriteTransactional;
import com.plyushkin.shared.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
public abstract class AbstractRecordUseCase<
        C extends AbstractCategory<C>,
        R extends AbstractRecord<C, R>,
        RN extends AbstractNumber<RN>,
        CN extends AbstractNumber<CN>
        > {
    private final AbstractRecordRepository<C, R, RN> repository;
    private final AbstractCategoryRepository<C, CN> categoryRepository;

    protected abstract R newRecord(RN number,
                                   WalletId walletId,
                                   UserId whoCreated,
                                   LocalDate date,
                                   Currency currency,
                                   Money amount,
                                   C category,
                                   String comment) throws AbstractRecord.InvalidRecordException;

    @WriteTransactional
    public R createRecord(CreateRecordCommand<CN> command) throws CreateRecordException {
        repository.lockByWalletId(command.walletId());
        final var number = repository.nextNumber(command.walletId());

        try {
            return repository.save(
                    newRecord(
                            number,
                            command.walletId(),
                            command.whoCreated(),
                            command.date(),
                            command.currency(),
                            command.amount(),
                            categoryRepository.findByWalletIdAndNumber(command.walletId(), command.categoryNumber())
                                    .orElseThrow(() -> new CreateRecordException(
                                            "Cannot find category WalletId=%s, number=%s"
                                                    .formatted(command.walletId(), number)
                                    )),
                            command.comment()
                    )
            );
        } catch (AbstractRecord.InvalidRecordException e) {
            throw new CreateRecordException(
                    "Cannot create record", e
            );
        }
    }

    @WriteTransactional
    public R updateRecord(UpdateRecordCommand<RN, CN> command) throws UpdateRecordException {
        final var rec = repository.findByWalletIdAndNumber(command.walletId(), command.number())
                .orElseThrow(() -> new UpdateRecordException(
                        "Cannot find record by WalletId=%s, number=%s"
                                .formatted(command.walletId(), command.number())
                ));
        try {
            rec.update(
                    command.date(),
                    command.currency(),
                    command.amount(),
                    categoryRepository.findByWalletIdAndNumber(command.walletId(), command.categoryNumber())
                            .orElseThrow(() -> new UpdateRecordException(
                                    "Cannot find category by WalletId=%s and ExpenseCategoryNumber=%s"
                                            .formatted(command.walletId(), command.categoryNumber())
                            )),
                    command.comment()
            );
            return rec;
        } catch (AbstractRecord.InvalidRecordCategoryException e) {
            throw new UpdateRecordException(
                    "Error during record update WalletId=%s, number=%s"
                            .formatted(command.walletId(), command.number()),
                    e
            );
        }
    }

    @WriteTransactional
    public void deleteRecord(WalletId walletId, RN number) throws DeleteRecordException {
        final var rec = repository.findByWalletIdAndNumber(walletId, number)
                .orElseThrow(() -> new DeleteRecordException(
                        "Cannot find record by WalletId=%s and ExpenseNumber=%s"
                                .formatted(walletId, number)
                ));
        repository.delete(rec);
    }

    @Transactional(readOnly = true)
    public Page<R> listRecords(ListRecordsCommand command) {
        final var pageRequest = PageRequest.of(command.pageNumber(), command.pageSize(), Sort.by(
                Sort.Order.asc("date"),
                Sort.Order.asc("pk")
        ));

        final var specification = repository.pageSpecification(command.walletId(), command.from(), command.to());
        return repository.findAll(specification, pageRequest);
    }

    @Transactional(readOnly = true)
    public R getRecord(WalletId walletId, RN number) throws RecordNotFoundException {
        return repository.findByWalletIdAndNumber(walletId, number)
                .orElseThrow(() -> new RecordNotFoundException(
                        "Cannot find record by WalletId=%s, number=%s"
                                .formatted(walletId, number)
                ));
    }
}
