package com.zakrodionov.practicalapp.app.features.posts.ui.postsList

import android.os.Parcelable
import com.zakrodionov.common.ui.rv.DiffItem
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.ScreenState
import com.zakrodionov.practicalapp.app.core.ScreenState.CONTENT
import com.zakrodionov.practicalapp.app.core.ScreenState.ERROR
import com.zakrodionov.practicalapp.app.core.ScreenState.STUB
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostsState(
    val posts: List<DiffItem>? = null,
    val page: Int = 0,
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable {
    val screenState: ScreenState
        get() = when {
            error != null -> ERROR
            posts?.isEmpty() ?: false -> STUB
            posts?.isNotEmpty() ?: false -> CONTENT
            else -> CONTENT
        }

    fun increasePage() = page + 1
}

sealed class PostsEvent
