package com.plyushkin.budget

import com.plyushkin.budget.expense.CategoryId
import com.plyushkin.common.AggregateRoot
import com.plyushkin.user.UserId
import com.plyushkin.wallet.WalletId

open class Category<T : Category<T>> protected constructor(
    val id: CategoryId,
    val walletId: WalletId,
    val whoCreated: UserId,
    children: Set<T>
) : AggregateRoot<Pair<CategoryId, WalletId>>(Pair(id, walletId)) {
    private val _children: MutableSet<T>
    val children: Set<T>
        get() = _children

    init {
        this._children = children.toMutableSet()
    }

    fun addChild(category: T): T {
        _children.add(category)

        return this as T
    }

    data class ChildCategoryAdded
}