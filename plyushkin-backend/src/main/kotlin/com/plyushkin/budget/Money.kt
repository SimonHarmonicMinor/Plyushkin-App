package com.plyushkin.budget

import arrow.core.Either
import java.math.BigDecimal

data class Money private constructor(val value: BigDecimal) {
    companion object {
        fun zero(): Money {
            return Money(BigDecimal.ZERO)
        }

        fun ofLong(value: Long): Either<Invalid, Money> {
            return ofBigDecimal(BigDecimal.valueOf(value))
        }

        fun ofBigDecimal(value: BigDecimal): Either<Invalid, Money> {
            if (value < BigDecimal.ZERO) {
                return Either.Left(Invalid("Amount of money cannot be less than zero: $value"))
            }
            return Either.Right(Money(value))
        }
    }

    fun plus(amount: Money): Money {
        return Money(value.plus(amount.value))
    }

    class Invalid(message: String) : IllegalArgumentException(message)
}