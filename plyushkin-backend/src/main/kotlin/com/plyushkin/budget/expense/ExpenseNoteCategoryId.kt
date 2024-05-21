package com.plyushkin.budget.expense

import arrow.core.Either

data class ExpenseNoteCategoryId internal constructor(val value: Long) {
    companion object {}
}

class InvalidExpenseNoteCategoryId(message: String) : IllegalArgumentException(message)

fun ExpenseNoteCategoryId.Companion.create(value: Long): Either<InvalidExpenseNoteCategoryId, ExpenseNoteCategoryId> =
    value.let {
        if (it <= 0) {
            Either.Left(InvalidExpenseNoteCategoryId("Value should be positive: $value"))
        } else {
            Either.Right(ExpenseNoteCategoryId(value))
        }
    }
