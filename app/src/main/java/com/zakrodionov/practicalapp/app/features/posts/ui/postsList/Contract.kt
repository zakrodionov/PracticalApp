package com.zakrodionov.practicalapp.app.features.posts.ui.postsList

import android.os.Parcelable
import com.zakrodionov.common.ui.lce.LceLayout.LceState
import com.zakrodionov.common.ui.lce.LceLayout.LceState.ContentState
import com.zakrodionov.common.ui.lce.LceLayout.LceState.EmptyState
import com.zakrodionov.common.ui.lce.LceLayout.LceState.ErrorState
import com.zakrodionov.common.ui.lce.LceLayout.LceState.LoadingState
import com.zakrodionov.common.ui.rv.DiffItem
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.toUiError
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsState(
    val posts: List<DiffItem>? = null,
    val page: Int = 0,
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable {
    val lceState: LceState
        get() = when {
            isLoading -> LoadingState(page > 1)
            error != null -> ErrorState(error.toUiError())
            posts?.isEmpty() ?: false -> EmptyState
            posts?.isNotEmpty() ?: false -> ContentState
            else -> ContentState
        }

    fun increasePage() = page + 1
}

sealed class PostsEvent
