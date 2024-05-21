package com.plyushkin.budget.expense

import arrow.core.Either
import com.plyushkin.common.DomainContext
import com.plyushkin.common.DomainEvent
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId

data class ExpenseNoteCategory private constructor(
    val id: ExpenseNoteCategoryId,
    val walletId: WalletId,
    val whoCreated: UserId,
    val children: Set<ExpenseNoteCategory>
) {
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

}

class CannotAddChildExpenseCategory(message: String) : IllegalArgumentException(message)

data class ChildCategoryAddedEvent(
    val walletId: WalletId,
    val parentCategoryId: ExpenseNoteCategoryId,
    val childCategoryId: ExpenseNoteCategoryId
) : DomainEvent

fun DomainContext<ExpenseNoteCategory>.withChildCategory(category: ExpenseNoteCategory)
        : Either<CannotAddChildExpenseCategory, DomainContext<ExpenseNoteCategory>> =
    entity.run {
        if (category.walletId != walletId) {
            return Either.Left(
                CannotAddChildExpenseCategory(
                    "Child.walletId=${category.walletId} does not equal to current walletId=${walletId}"
                )
            )
        }
        return Either.Right(
            withNewEvent(
                { it.copy(children = it.children + category) },
                ChildCategoryAddedEvent(
                    walletId = walletId,
                    parentCategoryId = id,
                    childCategoryId = category.id
                )
            )
        )
    }
