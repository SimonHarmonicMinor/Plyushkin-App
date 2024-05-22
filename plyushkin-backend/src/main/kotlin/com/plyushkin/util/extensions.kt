package com.plyushkin

import arrow.core.Either

class NothingToRemoveException(message: String) : IllegalArgumentException(message)

fun <T> Set<T>.withRemovedBy(predicate: (T) -> Boolean): Either<NothingToRemoveException, Set<T>> {
    val result = mutableSetOf<T>()
    this.forEach {
        if (!predicate(it)) {
            result.add(it)
        }
    }
    if (result.size == this.size) {
        return Either.Left(NothingToRemoveException("Nothing has been removed from the set"))
    }
    return Either.Right(result);
}