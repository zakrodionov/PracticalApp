package com.zakrodionov.practicalapp.app.features.posts.ui.postDetails

import android.os.Parcelable
import com.zakrodionov.common.ui.lce.LceLayout
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.toUiError
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailsState(
    val post: Post? = null,
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable {
    val lceState: LceLayout.LceState
        get() = when {
            isLoading -> LceLayout.LceState.LoadingState(error != null)
            error != null -> LceLayout.LceState.ErrorState(error.toUiError())
            else -> LceLayout.LceState.ContentState
        }
}

sealed class PostDetailsEvent
