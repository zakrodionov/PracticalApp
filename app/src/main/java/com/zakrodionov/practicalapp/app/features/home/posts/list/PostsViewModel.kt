package com.zakrodionov.practicalapp.app.features.home.posts.list

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.models.Loading
import com.zakrodionov.common.ui.addLoadingItem
import com.zakrodionov.common.ui.removeLoadingItem
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository
import kotlinx.coroutines.Job

class PostsViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val dispatchersProvider: DispatchersProvider,
) : BaseViewModel<PostsState, PostsEvent>(PostsState(), savedStateHandle, dispatchersProvider) {

    private var loadingPostsJob: Job = Job()

    init {
        if (state.posts == null) loadPosts(initial = true)
    }

    fun loadPosts(refresh: Boolean = false, initial: Boolean = false) {
        if (loadingPostsJob.isCompleted || initial) {
            loadingPostsJob = launchIo {
                if (refresh) reduce { state.copy(page = 0) }

                if (state.page > 0) {
                    reduce { state.copy(posts = state.posts?.addLoadingItem()) } // Show pagination loading
                } else {
                    reduce { state.copy(loading = Loading(true, refresh)) }
                }

                postRepository
                    .getPosts(state.page)
                    .onSuccess { posts ->
                        val newPosts = if (refresh) posts else state.posts.orEmpty().plus(posts)
                        reduce {
                            state.copy(
                                posts = newPosts, error = null, page = state.increasePage()
                            )
                        }
                    }
                    .onFailure {
                        reduce { state.copy(error = it) }
                    }

                // Hide all loaders
                reduce {
                    state.copy(
                        posts = state.posts?.removeLoadingItem(),
                        loading = Loading(isLoading = false, fromSwipeRefresh = false)
                    )
                }
            }
        }
    }
}
