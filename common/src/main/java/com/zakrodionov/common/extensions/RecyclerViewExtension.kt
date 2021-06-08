package com.zakrodionov.common.extensions

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.zakrodionov.common.extensions.RecyclerViewItemAnimation.ALL_ANIMATIONS
import com.zakrodionov.common.extensions.RecyclerViewItemAnimation.DISABLE_ALL_ANIMATIONS
import com.zakrodionov.common.extensions.RecyclerViewItemAnimation.DISABLE_CHANGE_ANIMATIONS

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

enum class RecyclerViewItemAnimation {
    ALL_ANIMATIONS,
    DISABLE_CHANGE_ANIMATIONS,
    DISABLE_ALL_ANIMATIONS
}

fun <T : RecyclerView.ViewHolder> RecyclerView.setup(
    adapter: RecyclerView.Adapter<T>,
    layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context),
    itemAnimation: RecyclerViewItemAnimation = ALL_ANIMATIONS,
    clipToPadding: Boolean = false,
) {
    this.layoutManager = layoutManager
    this.adapter = adapter
    this.clipToPadding = clipToPadding

    when (itemAnimation) {
        ALL_ANIMATIONS -> Unit
        DISABLE_CHANGE_ANIMATIONS -> (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        DISABLE_ALL_ANIMATIONS -> itemAnimator = null
    }
}
