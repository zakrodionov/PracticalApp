package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowDialog
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowSnackbar
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
) : BaseViewModel<PostDetailsState, PostDetailsEvent>(PostDetailsState(), savedStateHandle) {

    init {
        if (state.post == null) loadPostDetails()
    }

    fun loadPostDetails() = launch {
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

    override suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowDialog(baseError.title, baseError.message)))
            NON_CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowSnackbar(baseError.message)))
            CONTENT_ERROR -> reduce { state.copy(error = baseError) }
        }
    }
}
