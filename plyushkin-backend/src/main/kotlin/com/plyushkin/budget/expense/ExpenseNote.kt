package com.plyushkin.budget.expense

import arrow.core.Either
import com.plyushkin.budget.Money
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId
import java.time.LocalDate

class ExpenseNote internal constructor(
    val id: ExpenseNoteId,
    val walletId: WalletId,
    val whoDid: UserId,
    val date: LocalDate,
    val amount: Money,
    val category: ExpenseNoteCategory,
    val comment: String
) {



    companion object {
        fun create(
            id: ExpenseNoteId,
            walletId: WalletId,
            whoDid: UserId,
            date: LocalDate,
            amount: Money,
            category: ExpenseNoteCategory,
            comment: String
        ): Either<InvalidExpenseNote, ExpenseNote> {
            if (category.walletId != walletId) {
                return Either.Left(InvalidCategory("Category walletId=${category.walletId} does not equal to ExpenseNote walletId=$walletId"))
            }
            if (category.children.isNotEmpty()) {
                return Either.Left(InvalidCategory("Category is not terminated because it has children: ${category.children}"))
            }
            return Either.Right(ExpenseNote(id, walletId, whoDid, date, amount, category, comment))
        }
    }

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
}

open class InvalidExpenseNote(message: String) : IllegalArgumentException(message)

class InvalidCategory(message: String) : InvalidExpenseNote(message)


