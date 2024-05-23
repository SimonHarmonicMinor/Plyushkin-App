package com.plyushkin.budget.expense

import arrow.core.Either
import com.plyushkin.common.DomainContext
import com.plyushkin.common.DomainEvent
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId
import com.plyushkin.withRemovedBy

data class ExpenseNoteCategory internal constructor(
    val id: ExpenseNoteCategoryId,
    val walletId: WalletId,
    val whoCreated: UserId,
    val children: Set<ExpenseNoteCategory>
) {
    companion object;

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpenseNoteCategory

        if (id != other.id) return false
        if (walletId != other.walletId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + walletId.hashCode()
        return result
    }

    data class ChildCategoryAddedEvent(
        val walletId: WalletId,
        val parentCategoryId: ExpenseNoteCategoryId,
        val childCategoryId: ExpenseNoteCategoryId
    ) : DomainEvent

    data class ChildCategoryDetachedEvent(
        val walletId: WalletId,
        val parentCategoryId: ExpenseNoteCategoryId,
        val childCategoryId: ExpenseNoteCategoryId
    ) : DomainEvent

    class AddChildExpenseCategoryException(message: String) : IllegalArgumentException(message)

    class DetachChildExpenseCategoryException(message: String, cause: Throwable) :
        IllegalArgumentException(message, cause)
}

fun ExpenseNoteCategory.Companion.create(
    id: ExpenseNoteCategoryId,
    walletId: WalletId,
    whoCreated: UserId,
    children: Set<ExpenseNoteCategory>
) = ExpenseNoteCategory(id, walletId, whoCreated, children)

fun ExpenseNoteCategory.domainContext() = DomainContext(this, emptyList())

fun DomainContext<ExpenseNoteCategory>.addChildCategory(category: ExpenseNoteCategory)
        : Either<ExpenseNoteCategory.AddChildExpenseCategoryException, DomainContext<ExpenseNoteCategory>> =
    entity.run {
        if (category.walletId != walletId) {
            return Either.Left(
                ExpenseNoteCategory.AddChildExpenseCategoryException(
                    "Child.walletId=${category.walletId} does not equal to current walletId=${walletId}"
                )
            )
        }
        return Either.Right(
            withNewEvent(
                { it.copy(children = it.children + category) },
                ExpenseNoteCategory.ChildCategoryAddedEvent(
                    walletId = walletId,
                    parentCategoryId = id,
                    childCategoryId = category.id
                )
            )
        )
    }

fun DomainContext<ExpenseNoteCategory>.detachChildCategory(childCategoryId: ExpenseNoteCategoryId)
        : Either<ExpenseNoteCategory.DetachChildExpenseCategoryException, DomainContext<ExpenseNoteCategory>> =
    entity.children.withRemovedBy { it.id == childCategoryId }
        .map { newChildren ->
            withNewEvent(
                { it.copy(children = newChildren) },
                ExpenseNoteCategory.ChildCategoryDetachedEvent(
                    walletId = entity.walletId,
                    parentCategoryId = entity.id,
                    childCategoryId = childCategoryId
                )
            )
        }
        .mapLeft {
            ExpenseNoteCategory.DetachChildExpenseCategoryException(
                "Cannot find child category with the specified id to detach=$childCategoryId",
                it
            )
        }

