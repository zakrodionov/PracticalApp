package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Parcelable
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.ScreenState
import com.zakrodionov.practicalapp.app.core.ScreenState.CONTENT
import com.zakrodionov.practicalapp.app.core.ScreenState.ERROR
import com.zakrodionov.practicalapp.app.core.ShowAction
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailsState(
    val post: Post? = null,
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable {
    val screenState: ScreenState
        get() = when {
            error != null -> ERROR
            else -> CONTENT
        }
}

sealed class PostDetailsEvent {
    data class ShowEvent(val showAction: ShowAction) : PostDetailsEvent()
}
