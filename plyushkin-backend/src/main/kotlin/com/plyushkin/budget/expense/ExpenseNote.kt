package com.plyushkin.budget.expense

import arrow.core.Either
import com.plyushkin.budget.Category
import com.plyushkin.budget.Money
import com.plyushkin.common.BaseEntity
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId
import java.time.LocalDate

data class CategoryId private constructor(val value: Long) {
    companion object {
        fun create(value: Long): Either<Invalid, CategoryId> {
            return value.let {
                if (it <= 0) {
                    Either.Left(Invalid("Value should be positive: $value"))
                } else {
                    Either.Right(CategoryId(value))
                }
            }
        }
    }

    class Invalid(message: String) : IllegalArgumentException(message)
}

data class ExpenseNoteId private constructor(val value: Long) {
    companion object {
        fun create(value: Long): Either<Invalid, ExpenseNoteId> {
            return value.let {
                if (it <= 0) {
                    Either.Left(Invalid("Value should be positive: $value"))
                } else {
                    Either.Right(ExpenseNoteId(value))
                }
            }
        }
    }

    class Invalid(message: String) : IllegalArgumentException(message)
}

class ExpenseNoteCategory(
    id: CategoryId,
    walletId: WalletId,
    whoCreated: UserId,
    children: Set<ExpenseNoteCategory>
) : Category<ExpenseNoteCategory>(
    id, walletId, whoCreated, children
)

class ExpenseNote private constructor(
    val id: ExpenseNoteId,
    val walletId: WalletId,
    val whoDid: UserId,
    val date: LocalDate,
    val amount: Money,
    val category: ExpenseNoteCategory,
    val comment: String
) : BaseEntity<Pair<ExpenseNoteId, WalletId>>(Pair(id, walletId)) {
    companion object {
        fun create(
            id: ExpenseNoteId,
            walletId: WalletId,
            whoDid: UserId,
            date: LocalDate,
            amount: Money,
            category: ExpenseNoteCategory,
            comment: String
        ): Either<Invalid, ExpenseNote> {
            if (category.walletId != walletId) {
                return Either.Left(InvalidCategory("Category walletId=${category.walletId} does not equal to ExpenseNote walletId=$walletId"))
            }
            if (category.children.isNotEmpty()) {
                return Either.Left(InvalidCategory("Category is not terminated because it has children: ${category.children}"))
            }
            return Either.Right(ExpenseNote(id, walletId, whoDid, date, amount, category, comment))
        }
    }

    open class Invalid(message: String) : IllegalArgumentException(message)

    class InvalidCategory(message: String) : Invalid(message)
}
