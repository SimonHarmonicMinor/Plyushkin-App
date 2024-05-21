package com.plyushkin.budget.expense

import arrow.core.Either

data class ExpenseNoteId internal constructor(val value: Long) {
    companion object {}
}

class InvalidExpenseNoteId(message: String) : IllegalArgumentException(message)

fun ExpenseNoteId.Companion.create(value: Long): Either<InvalidExpenseNoteId, ExpenseNoteId> =
    value.let {
        if (it <= 0) {
            Either.Left(InvalidExpenseNoteId("Value should be positive: $value"))
        } else {
            Either.Right(ExpenseNoteId(value))
        }
    }

