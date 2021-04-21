package com.zakrodionov.practicalapp.app.ui.posts

import androidx.lifecycle.SavedStateHandle
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.externalForward
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.practicalapp.app.core.BaseError
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.ImportanceError.CONTENT_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ImportanceError.NON_CRITICAL_ERROR
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowDialog
import com.zakrodionov.practicalapp.app.core.ShowAction.ShowSnackbar
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.ui.Screens
import com.zakrodionov.practicalapp.app.ui.postDetails.ArgsPostDetail
import com.zakrodionov.practicalapp.domain.repository.PostRepository

class PostsViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val modo: Modo
) : BaseViewModel<PostsState, PostsEvent>(PostsState(), savedStateHandle) {

    init {
        if (state.posts == null) loadPosts()
    }

    fun loadPosts(refresh: Boolean = false) = launch {
        if (!state.isLoading) {
            reduce { state.copy(isLoading = true) }

            if (refresh) reduce { state.copy(page = 0) }

            postRepository
                .getPosts(state.page)
                .onSuccess { posts ->
                    val newPosts = if (refresh) posts else state.posts.orEmpty().plus(posts)
                    reduce { state.copy(posts = newPosts, error = null, page = state.increasePage()) }
                }
                .onFailure {
                    handleError(it)
                }
            reduce { state.copy(isLoading = false) }
        }
    }

    fun navigateToPost(postId: String?) {
        postId.ifNotNull {
            modo.externalForward(Screens.PostDetailScreen(ArgsPostDetail(it)))
        }
    }

    override suspend fun handleError(baseError: BaseError) {
        when (baseError.importanceError) {
            CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowDialog(baseError.title, baseError.message)))
            NON_CRITICAL_ERROR -> postShowEvent(ShowEvent(ShowSnackbar(baseError.message)))
            CONTENT_ERROR -> reduce { state.copy(error = baseError) }
        }
    }
}
