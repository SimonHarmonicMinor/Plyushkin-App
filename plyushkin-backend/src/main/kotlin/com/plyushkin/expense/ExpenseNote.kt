package com.plyushkin.expense

import arrow.core.Either
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId

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

class ExpenseNote(val id: ExpenseNoteId,
                  val walletId: WalletId,
                  val whoDid: UserId,
                  val categoryId: CategoryId) {
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

class Category(val id: CategoryId,
               val walletId: WalletId,
               val whoCreated: UserId,
               val parent: Category?) {
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