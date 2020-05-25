package com.arthe100.arshop.views.adapters.base

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val itemDiff: GenericItemDiff<T>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return itemDiff.areItemsTheSame(old, new)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]
        return itemDiff.areContentsTheSame(old , new)
    }
}

interface GenericItemDiff<T>{
    fun areItemsTheSame(oldItem: T, newItem: T): Boolean
    fun areContentsTheSame(oldItem: T, newItem: T): Boolean
}