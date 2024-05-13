package com.plyushkin.user

import arrow.core.Either
import com.plyushkin.wallet.WalletId
import java.util.concurrent.ThreadLocalRandom

data class UserId private constructor(val value: String) {
    companion object {
        fun createRandom(): UserId {
            val randomNum = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE)
            return create("U$randomNum").getOrNull()!!
        }

        fun create(value: String): Either<InvalidUserId, UserId> {
            if (!value.startsWith("U")) {
                return Either.Left(InvalidUserId("UserId must start with U: $value"))
            }
            return value.substring(3).toLongOrNull().let {
                if (it == null) {
                    Either.Left(InvalidUserId("UserId must contain a number: $value"))
                } else {
                    Either.Right(UserId(value))
                }
            }
        }
    }

    class InvalidUserId(message: String) : IllegalArgumentException(message)
}

enum class Permission {
    READ, WRITE
}

class User(val id: UserId, val permissions: Map<WalletId, Set<Permission>>) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}