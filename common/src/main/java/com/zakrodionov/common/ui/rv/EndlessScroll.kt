package com.zakrodionov.common.ui.rv

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

// Based on CodePath (https://gist.github.com/nesquena/d09dc68ff07e845cc622)
open class EndlessScroll(
    private val layoutManager: RecyclerView.LayoutManager,
    private var visibleThreshold: Int = DEFAULT_VISIBLE_THRESHOLD,
    private val onLoadMore: () -> Unit,
) : RecyclerView.OnScrollListener() {

    private var loading = false

    init {
        when (layoutManager) {
            is GridLayoutManager -> visibleThreshold *= layoutManager.spanCount
            is StaggeredGridLayoutManager -> visibleThreshold *= layoutManager.spanCount
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount

        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager ->
                lastVisibleItemPosition =
                    layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager ->
                lastVisibleItemPosition =
                    layoutManager.findLastVisibleItemPosition()
        }

        if (lastVisibleItemPosition + visibleThreshold > totalItemCount && !loading) {
            onLoadMore()
        }
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    companion object {
        // The minimum amount of items to have below your current scroll position before loading more.
        const val DEFAULT_VISIBLE_THRESHOLD = 5
    }
}
