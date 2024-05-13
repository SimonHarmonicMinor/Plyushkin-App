package com.plyushkin.expense

import arrow.core.Either
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId

data class ExpenseNoteId private constructor(val value: Long) {
    companion object {
        fun create(value: Long): Either<InvalidExpenseNoteId, ExpenseNoteId> {
            return value.let {
                if (it <= 0) {
                    Either.Left(InvalidExpenseNoteId("Value should be positive: $value"))
                } else {
                    Either.Right(ExpenseNoteId(value))
                }
            }
        }
    }

    class InvalidExpenseNoteId(message: String) : IllegalArgumentException(message)
}

class ExpenseNote(val id: ExpenseNoteId, val walletId: WalletId, val whoDid: UserId) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpenseNote

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}