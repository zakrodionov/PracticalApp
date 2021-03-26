package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Parcelable
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ShowAction
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseState
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailsState(
    val post: Post? = null,
    override val error: BaseError? = null,
    override val isLoading: Boolean = false
) : Parcelable, BaseState {
    val screenState: ScreenState
        get() = when {
            error != null -> ERROR
            else -> CONTENT
        }
}

sealed class PostDetailsEvent {
    data class ShowEvent(val showAction: ShowAction) : PostDetailsEvent()
}
