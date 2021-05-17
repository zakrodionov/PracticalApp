package com.zakrodionov.common.ui.rv

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.zakrodionov.common.databinding.ItemLoadingBinding
import kotlinx.parcelize.Parcelize

const val LOADING_ITEM_ID = "LoadingItem"
const val LOADING_SHIMMER_ITEM_ID = "LoadingShimmerItem"

@Parcelize
object LoadingItem : DiffItem {
    override val itemId: String get() = LOADING_ITEM_ID
}

@Parcelize
data class LoadingShimmerItem(val idPostfix: String) : DiffItem {
    override val itemId: String get() = "$LOADING_SHIMMER_ITEM_ID-$idPostfix"
}

fun loadingDelegate() = adapterDelegateViewBinding<LoadingItem, DiffItem, ItemLoadingBinding>(
    { inflater, root -> ItemLoadingBinding.inflate(inflater, root, false) }) {}

fun List<DiffItem>?.addLoadingItem(): List<DiffItem> = (this?.toMutableList() ?: mutableListOf()).apply {
    // Remove item ig already showing
    remove(LoadingItem)
    add(LoadingItem)
}

fun List<DiffItem>.removeLoadingItem(): List<DiffItem> = toMutableList().apply {
    remove(LoadingItem)
}

fun List<DiffItem>?.addLoadingShimmerItems(count: Int = 10): List<DiffItem> =
    (this?.toMutableList() ?: mutableListOf()).apply {
        clear()
        (0..count).forEachIndexed { index, _ -> add(LoadingShimmerItem("$index")) }
    }

fun List<DiffItem>.removeLoadingShimmerItems(): List<DiffItem> = toMutableList().apply {
    removeAll { it is LoadingShimmerItem }
}