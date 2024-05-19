package com.plyushkin.expense

import arrow.core.Either
import arrow.core.NonEmptySet
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId

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

sealed class Category(
    val id: CategoryId,
    val walletId: WalletId,
    val whoCreated: UserId
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

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

class LeafCategory(
    id: CategoryId,
    walletId: WalletId,
    whoCreated: UserId,
    val parent: Category?
) : Category(id, walletId, whoCreated)

class NonLeafCategory(
    id: CategoryId,
    walletId: WalletId,
    whoCreated: UserId,
    val parent: Category?,
    val children: NonEmptySet<Category>
) : Category(id, walletId, whoCreated)


class ExpenseNote private constructor(
    val id: ExpenseNoteId,
    val walletId: WalletId,
    val whoDid: UserId,
    val category: LeafCategory,
    val comment: String
) {
    companion object {
        fun create(
            id: ExpenseNoteId,
            walletId: WalletId,
            whoDid: UserId,
            category: LeafCategory,
            comment: String
        ): Either<Invalid, ExpenseNote> {
            if (category.walletId != walletId) {
                return Either.Left(InvalidCategory("Category walletId=${category.walletId} does not equal to ExpenseNote walletId=$walletId"))
            }
            return Either.Right(ExpenseNote(id, walletId, whoDid, category, comment))
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

    open class Invalid(message: String) : IllegalArgumentException(message)

    class InvalidCategory(message: String) : Invalid(message)
}
