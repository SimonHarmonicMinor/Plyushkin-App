package com.plyushkin.budget

import arrow.core.Either
import java.math.BigDecimal

data class Money internal constructor(val value: BigDecimal) {
    companion object

    class InvalidException(message: String) : IllegalArgumentException(message)
}

fun Money.Companion.zero(): Money = Money(BigDecimal.ZERO)

fun Money.Companion.ofLong(value: Long): Either<Money.InvalidException, Money> = ofBigDecimal(BigDecimal.valueOf(value))

fun Money.Companion.ofBigDecimal(value: BigDecimal): Either<Money.InvalidException, Money> =
    if (value < BigDecimal.ZERO) {
        Either.Left(Money.InvalidException("Amount of money cannot be less than zero: $value"))
    } else {
        Either.Right(Money(value))
    }

fun Money.plus(amount: Money): Money = Money(value.plus(amount.value))