package com.zakrodionov.practicalapp.app.features.home.posts.ui.list

import android.os.Parcelable
import com.zakrodionov.common.ui.DiffItem
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.EmptyState
import com.zakrodionov.common.ui.lce.ErrorState
import com.zakrodionov.common.ui.lce.LceState
import com.zakrodionov.common.ui.lce.LoadingState
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.toUiError
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsState(
    val posts: List<DiffItem>? = null,
    val page: Int = 0,
    val error: BaseError? = null,
    val isLoading: Boolean = false,
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
