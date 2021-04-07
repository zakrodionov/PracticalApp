package com.zakrodionov.common.ui.rv

import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

@Suppress("SpreadOperator")
class AsyncListDifferProgressAdapter(vararg delegate: AdapterDelegate<List<DiffItem>>) :
    AsyncListDifferDelegationAdapter<DiffItem>(DiffCallback, *delegate, loadingDelegate()) {

    override fun setItems(items: MutableList<DiffItem>?) {
        if (items?.contains(LoadingItem) == true) items.remove(LoadingItem)
        super.setItems(items)
    }

    fun showLoading() {
        if (!items.isNullOrEmpty()) {
            val adapterItems = if (items.contains(LoadingItem)) items else items.plusElement(LoadingItem)
            super.setItems(adapterItems)
        }
    }
}
