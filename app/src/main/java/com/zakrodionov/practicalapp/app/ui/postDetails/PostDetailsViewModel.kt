package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.ui.ShowAction.ShowDialog
import com.zakrodionov.common.ui.ShowAction.ShowSnackbar
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.domain.repository.PostRepository
import kotlinx.parcelize.Parcelize
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@Parcelize
data class ArgsPostDetail(val postId: String) : Parcelable

class PostDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val args: ArgsPostDetail
) : BaseViewModel<PostDetailsState, PostDetailsEvent>() {

    override val container =
        container<PostDetailsState, PostDetailsEvent>(PostDetailsState(), savedStateHandle) { state ->
            if (state.post == null) {
                loadPostDetails()
            }
        }

    fun loadPostDetails() = intent {
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

    // region Обработка ошибок
    override fun onCriticalError(baseError: BaseError) = intent {
        postSideEffect(PostDetailsEvent.ShowEvent(ShowDialog(baseError.title, baseError.message)))
    }

    override fun onNonCriticalError(baseError: BaseError) = intent {
        postSideEffect(PostDetailsEvent.ShowEvent(ShowSnackbar(baseError.message)))
    }

    override fun onContentError(baseError: BaseError) = intent {
        reduce { state.copy(error = baseError) }
    }
    //endregion
}
