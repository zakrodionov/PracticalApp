package com.zakrodionov.practicalapp.app.features.posts.ui.detail

import android.os.Parcelable
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.ErrorState
import com.zakrodionov.common.ui.lce.LceState
import com.zakrodionov.common.ui.lce.LoadingState
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
    val lceState: LceState
        get() = when {
            isLoading -> LoadingState(error != null)
            error != null -> ErrorState(error.toUiError())
            else -> ContentState
        }
}

sealed class PostDetailsEvent
