package com.zakrodionov.practicalapp.app.features.home.posts.detail

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ArgsPostDetail(val postId: String, val post: Post? = null) : Parcelable, Serializable

class PostDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val args: ArgsPostDetail,
) : BaseViewModel<PostDetailsState, PostDetailsEvent>(
    PostDetailsState(post = args.post), savedStateHandle
) {

    init {
        if (state.post == null) loadPostDetails()
    }

    fun loadPostDetails() = launchIo {
        reduce { state.copy(isLoading = true) }
        postRepository
            .getPost(args.postId)
            .onSuccess { post ->
                reduce { state.copy(post = post, error = null) }
            }
            .onFailure {
                reduce { state.copy(error = it) }
            }
        reduce { state.copy(isLoading = false) }
    }
}
