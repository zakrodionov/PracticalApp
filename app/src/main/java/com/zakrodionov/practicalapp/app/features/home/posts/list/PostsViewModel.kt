package com.zakrodionov.practicalapp.app.features.home.posts.list

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
    // savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    dispatchersProvider: DispatchersProvider,
) : BaseViewModel<PostsState, PostsEvent>(PostsState(), null, dispatchersProvider) {

    private var loadingPostsJob: Job = Job()

    init {
        if (state.posts == null) loadPosts(initial = true)
    }

    fun loadPosts(refresh: Boolean = false, initial: Boolean = false) {
        if (loadingPostsJob.isCompleted || initial) {
            loadingPostsJob = launchIo {
                if (refresh) update { it.copy(page = 0) }

                if (state.page > 0) {
                    update { it.copy(posts = it.posts?.addLoadingItem()) } // Show pagination loading
                } else {
                    update { it.copy(loading = Loading(true, refresh)) }
                }

                postRepository
                    .getPosts(state.page)
                    .onSuccess { posts ->
                        update {
                            val newPosts = if (refresh) posts else it.posts.orEmpty().plus(posts)
                            it.copy(posts = newPosts, error = null, page = it.increasePage())
                        }
                    }
                    .onFailure { error ->
                        update { it.copy(error = error) }
                    }

                // Hide all loaders
                update {
                    it.copy(
                        posts = it.posts?.removeLoadingItem(),
                        loading = Loading(isLoading = false, fromSwipeRefresh = false)
                    )
                }
            }
        }
    }
}
