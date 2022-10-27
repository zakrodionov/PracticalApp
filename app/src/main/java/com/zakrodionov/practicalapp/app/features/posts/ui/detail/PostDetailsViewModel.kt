package com.zakrodionov.practicalapp.app.features.posts.ui.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgsPostDetail(val postId: String, val post: Post? = null) : Parcelable

class PostDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val args: ArgsPostDetail
) : BaseViewModel<PostDetailsState, PostDetailsEvent>(
    PostDetailsState(post = args.post),
    savedStateHandle
) {

    init {
        if (state.post == null) loadPostDetails()
    }

    fun loadPostDetails() = launchIo {
        update { it.copy(isLoading = true) }
        postRepository
            .getPost(args.postId)
            .onSuccess { post ->
                update { it.copy(post = post, error = null) }
            }
            .onFailure { error ->
                update { it.copy(error = error) }
            }
        update { it.copy(isLoading = false) }
    }
}
