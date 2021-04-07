package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.domain.repository.PostRepository
import kotlinx.parcelize.Parcelize

@Parcelize
data class ArgsPostDetail(val postId: String) : Parcelable

class PostDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val args: ArgsPostDetail
) : BaseViewModel<PostDetailsState, PostDetailsEvent>(savedStateHandle) {

    override fun getInitialState(): PostDetailsState = PostDetailsState()

    init {
        if (state.post == null) loadPostDetails()
    }

    fun loadPostDetails() = launchUi {
        reduce { state.copy(isLoading = true) }
        postRepository
            .getPost(args.postId)
            .onSuccess { post ->
                reduce { state.copy(post = post, error = null) }
            }
            .onFailure {
                handleError(it)
            }
        reduce { state.copy(isLoading = false) }
    }
}
