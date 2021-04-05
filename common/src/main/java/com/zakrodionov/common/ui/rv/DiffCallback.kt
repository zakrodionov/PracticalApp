package com.zakrodionov.common.ui.rv

import androidx.recyclerview.widget.DiffUtil

object DiffCallback : DiffUtil.ItemCallback<DiffItem>() {

    override fun areItemsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
        return oldItem.hashCode() == newItem.hashCode()
    }
}
