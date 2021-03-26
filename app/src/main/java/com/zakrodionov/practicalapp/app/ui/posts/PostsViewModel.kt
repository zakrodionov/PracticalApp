package com.zakrodionov.practicalapp.app.ui.posts

import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.externalForward
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.ui.ShowAction.ShowDialog
import com.zakrodionov.common.ui.ShowAction.ShowSnackbar
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.ui.Screens
import com.zakrodionov.practicalapp.app.ui.postDetails.ArgsPostDetail
import com.zakrodionov.practicalapp.app.ui.posts.PostsEvent.ShowEvent
import com.zakrodionov.practicalapp.domain.repository.PostRepository
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

class PostsViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val modo: Modo
) : BaseViewModel<PostsState, PostsEvent>() {

    override val container = container<PostsState, PostsEvent>(PostsState(), savedStateHandle) { state ->
        if (state.posts == null) {
            loadPosts()
        }
    }

    fun loadPosts() = intent {
        reduce { state.copy(isLoading = true) }
        postRepository
            .getPosts()
            .onSuccess { posts ->
                reduce { state.copy(posts = posts, error = null) }
            }
            .onFailure {
                handleError(it)
            }
        reduce { state.copy(isLoading = false) }
    }

    fun navigateToPost(postId: String?) {
        postId.ifNotNull {
            modo.externalForward(Screens.PostDetailScreen(ArgsPostDetail(it)))
        }
    }

    // region Обработка ошибок
    override fun onCriticalError(baseError: BaseError) = intent {
        postSideEffect(ShowEvent(ShowDialog(baseError.title, baseError.message)))
    }

    override fun onNonCriticalError(baseError: BaseError) = intent {
        postSideEffect(ShowEvent(ShowSnackbar(baseError.message)))
    }

    override fun onContentError(baseError: BaseError) = intent {
        reduce { state.copy(error = baseError) }
    }
    //endregion
}
