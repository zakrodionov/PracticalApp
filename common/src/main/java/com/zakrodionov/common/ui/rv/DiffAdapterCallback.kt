package com.zakrodionov.common.ui.rv

import androidx.recyclerview.widget.DiffUtil

/**
 * It is highly recommended that type [T] is a Data class,
 * or at the very least overrides [Any.equals] and [Any.hashCode]
 */
open class DiffAdapterCallback<T> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        if (oldItem is DiffItem && newItem is DiffItem) {
            oldItem.itemId == newItem.itemId
        } else {
            false
        }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        oldItem.hashCode() == newItem.hashCode()
}

object DiffCallback : DiffAdapterCallback<DiffItem>()
