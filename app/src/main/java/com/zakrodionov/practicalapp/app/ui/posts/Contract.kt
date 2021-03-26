package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Parcelable
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ScreenState.STUB
import com.zakrodionov.common.ui.ShowAction
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseState
import com.zakrodionov.practicalapp.domain.model.Posts
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsState(
    val posts: List<Posts.Post>? = null,
    override val error: BaseError? = null,
    override val isLoading: Boolean = false
) : Parcelable, BaseState {
    val screenState: ScreenState
        get() = when {
            error != null -> ERROR
            posts?.isEmpty() ?: false -> STUB
            posts?.isNotEmpty() ?: false -> CONTENT
            else -> CONTENT
        }
}

sealed class PostsEvent {
    data class ShowEvent(val showAction: ShowAction) : PostsEvent()
}
