package com.zakrodionov.practicalapp.app.ui.postDetails

import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseState
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailsState(
    val post: Post? = null,
    override val error: BaseError? = null,
    override val isLoading: Boolean = false
) : BaseState {
    val screenState: ScreenState
        get() = when {
            error != null -> ERROR
            else -> CONTENT
        }

    override fun applyError(error: BaseError): PostDetailsState = copy(error = error)
}

sealed class PostDetailsEvent
