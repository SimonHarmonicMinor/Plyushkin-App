package com.plyushkin.budget.expense

import arrow.core.Either
import com.plyushkin.budget.Money
import com.plyushkin.common.DomainContext
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId
import java.time.LocalDate

data class ExpenseNote internal constructor(
    val id: ExpenseNoteId,
    val walletId: WalletId,
    val whoDid: UserId,
    val date: LocalDate,
    val amount: Money,
    val category: ExpenseNoteCategory,
    val comment: String
) {
    companion object;

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpenseNote

        if (id != other.id) return false
        if (walletId != other.walletId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + walletId.hashCode()
        return result
    }

    open class InvalidExpenseNoteException(message: String) : IllegalArgumentException(message)

    class InvalidCategoryException(message: String) : InvalidExpenseNoteException(message)
}

fun ExpenseNote.Companion.create(
    id: ExpenseNoteId,
    walletId: WalletId,
    whoDid: UserId,
    date: LocalDate,
    amount: Money,
    category: ExpenseNoteCategory,
    comment: String
): Either<ExpenseNote.InvalidExpenseNoteException, ExpenseNote> =
    ExpenseNote(id, walletId, whoDid, date, amount, category, comment)
        .validateCategory()

private fun ExpenseNote.validateCategory(): Either<ExpenseNote.InvalidCategoryException, ExpenseNote> {
    if (category.walletId != walletId) {
        return Either.Left(ExpenseNote.InvalidCategoryException("Category walletId=${category.walletId} does not equal to ExpenseNote walletId=$walletId"))
    }
    if (category.children.isNotEmpty()) {
        return Either.Left(ExpenseNote.InvalidCategoryException("Category cannot be assigned for expense note because it has children: ${category.children}"))
    }
    return Either.Right(this)
}

fun ExpenseNote.domainContext(): DomainContext<ExpenseNote> = DomainContext(this, emptyList())

fun DomainContext<ExpenseNote>.update(
    date: LocalDate,
    amount: Money,
    category: ExpenseNoteCategory,
    comment: String
): Either<ExpenseNote.InvalidExpenseNoteException, DomainContext<ExpenseNote>> {
    return entity.run {
        copy(date = date, amount = amount, category = category, comment = comment)
            .validateCategory()
    }.map { copy(entity = it) }
}

