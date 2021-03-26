package com.zakrodionov.common.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun <T : RecyclerView.ViewHolder> RecyclerView.setup(
    adapter: RecyclerView.Adapter<T>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    disableChangeAnimation: Boolean = false,
    disableAllAnimation: Boolean = false,
    clipToPadding: Boolean = false
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.clipToPadding = clipToPadding

    if (disableChangeAnimation) {
        (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
    }

    if (disableAllAnimation) {
        itemAnimator = null
    }
}
