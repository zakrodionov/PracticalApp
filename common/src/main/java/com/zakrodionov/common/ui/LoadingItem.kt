package com.zakrodionov.common.ui

import kotlinx.parcelize.Parcelize

const val LOADING_ITEM_ID = "LoadingItem"

@Parcelize
object LoadingItem : DiffItem {
    override val itemId: String get() = LOADING_ITEM_ID
}

fun List<DiffItem>.addLoadingItem(): List<DiffItem> = toMutableList().apply {
    // Remove item is already showing
    remove(LoadingItem)
    add(LoadingItem)
}

fun List<DiffItem>.removeLoadingItem(): List<DiffItem> = toMutableList().apply {
    remove(LoadingItem)
}
