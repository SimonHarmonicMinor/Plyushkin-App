package com.plyushkin.common

import java.util.concurrent.ThreadLocalRandom

interface DomainEvent

data class DomainContext<E>(val entity: E, val events: List<DomainEvent>) {
    fun withNewEvent(entityFn: (E) -> E, eventToAdd: DomainEvent): DomainContext<E> {
        return copy(entity = entityFn(entity), events = events + eventToAdd)
    }
}

open class BaseEntity<T>(private val id: T) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BaseEntity<*>

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

abstract class AggregateRoot<T>(id: T) : BaseEntity<T>(id) {
    private val _domainEvents = mutableListOf<DomainEvent>()
    val domainEvents: List<DomainEvent>
        get() = _domainEvents

    protected fun registerEvent(event: DomainEvent) {
        _domainEvents.add(event)
    }

    fun clearEvents() {
        _domainEvents.clear()
    }
}

open class PrefixedId {
    val value: String

    protected constructor(prefix: String) : this(
        prefix,
        prefix + ThreadLocalRandom
            .current()
            .nextLong(0, Long.MAX_VALUE)
            .toString()
            .padStart(19, '0')
    )

    protected constructor(prefix: String, value: String) {
        if (!value.startsWith(prefix)) {
            throw Invalid("Invalid Id: must start with $prefix: $value", null)
        }
        if (value.length != 20) {
            throw Invalid("Id length must be 20: $value", null)
        }
        value.substring(prefix.length).toLongOrNull().let {
            if (it == null) {
                throw Invalid("Id must contain a number: $value", null)
            } else if (it < 0) {
                throw Invalid("Id value must be positive: $value", null)
            } else {
                this.value = value
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PrefixedId

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    open class Invalid(message: String, cause: Throwable?) :
        IllegalArgumentException(message, cause)
}