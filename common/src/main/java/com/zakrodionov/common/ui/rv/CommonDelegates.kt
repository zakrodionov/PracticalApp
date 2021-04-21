package com.zakrodionov.common.ui.rv

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.zakrodionov.common.databinding.ItemLoadingBinding
import kotlinx.parcelize.Parcelize

const val LOADING_ITEM_ID = "LoadingItem"

@Parcelize
object LoadingItem : DiffItem {
    override val itemId: String get() = LOADING_ITEM_ID
}

fun loadingDelegate() = adapterDelegateViewBinding<LoadingItem, DiffItem, ItemLoadingBinding>(
    { inflater, root -> ItemLoadingBinding.inflate(inflater, root, false) }) {}

fun List<DiffItem>.addLoadingItem(): List<DiffItem> = toMutableList().apply {
    // Remove item ig already showing
    remove(LoadingItem)
    add(LoadingItem)
}

fun List<DiffItem>.removeLoadingItem(): List<DiffItem> = toMutableList().apply {
    remove(LoadingItem)
}
