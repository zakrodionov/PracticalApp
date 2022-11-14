package com.zakrodionov.common.ui

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember

const val PAGINATION_THRESHOLD = 5

@Composable
fun EndlessScrollListener(
    listState: LazyListState,
    paginationThreshold: Int = PAGINATION_THRESHOLD,
    onLoadMore: () -> Unit,
) {
    val totalItemsCount = remember { derivedStateOf { listState.layoutInfo.totalItemsCount } }
    val firstVisibleItemIndex = remember { derivedStateOf { listState.firstVisibleItemIndex } }
    if (totalItemsCount.value != 0 && firstVisibleItemIndex.value + paginationThreshold > totalItemsCount.value) {
        onLoadMore.invoke()
    }
}
