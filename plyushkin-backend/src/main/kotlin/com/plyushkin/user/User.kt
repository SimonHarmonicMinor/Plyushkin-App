package com.plyushkin.user

import arrow.core.Either
import com.plyushkin.common.BaseEntity
import com.plyushkin.common.PrefixedId
import com.plyushkin.wallet.WalletId

class UserId : PrefixedId {
    private constructor() : super("U")

    private constructor(value: String) : super("U", value)

    companion object {
        fun createRandom(): UserId = UserId()

        fun create(value: String): Either<InvalidUserId, UserId> {
            return try {
                Either.Right(UserId(value))
            } catch (e: Invalid) {
                Either.Left(
                    InvalidUserId(
                        "Cannot create UserId with invalid value: $value",
                        e
                    )
                )
            }
        }
    }

    class InvalidUserId(message: String, cause: Throwable) : Invalid(message, cause)
}

enum class Permission {
    READ, WRITE
}

class User(id: UserId, val permissions: Map<WalletId, Set<Permission>>) :
    BaseEntity<UserId>(id) {
}