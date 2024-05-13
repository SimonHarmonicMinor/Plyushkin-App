package com.plyushkin.wallet

import arrow.core.Either
import java.util.concurrent.ThreadLocalRandom

data class WalletId private constructor(val value: String) {
    companion object {
        fun createRandom(): WalletId {
            val randomNum = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE)
            return create("WA$randomNum").getOrNull()!!
        }

        fun create(value: String): Either<InvalidWalletId, WalletId> {
            if (!value.startsWith("WA")) {
                return Either.Left(InvalidWalletId("WalletId must start with WA: $value"))
            }
            return value.substring(3).toLongOrNull().let {
                if (it == null) {
                    Either.Left(InvalidWalletId("WalletId must contain a number: $value"))
                } else {
                    Either.Right(WalletId(value))
                }
            }
        }
    }

    class InvalidWalletId(message: String) : IllegalArgumentException(message)
}

enum class Currency {
    RUBLE, DOLLAR, EURO
}

class Wallet(val id: WalletId, val currency: Currency) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Wallet

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}