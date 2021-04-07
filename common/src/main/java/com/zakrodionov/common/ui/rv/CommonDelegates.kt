package com.zakrodionov.common.ui.rv

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.zakrodionov.common.databinding.ItemLoadingBinding

const val LOADING_ITEM_ID = "LoadingItem"

object LoadingItem : DiffItem {
    override val itemId: String = LOADING_ITEM_ID
}

fun loadingDelegate() = adapterDelegateViewBinding<LoadingItem, DiffItem, ItemLoadingBinding>(
    { inflater, root -> ItemLoadingBinding.inflate(inflater, root, false) }) {}
