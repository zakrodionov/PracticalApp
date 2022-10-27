package com.zakrodionov.practicalapp.app.features.posts.ui.list

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.ui.rv.addLoadingItem
import com.zakrodionov.common.ui.rv.removeLoadingItem
import com.zakrodionov.practicalapp.app.core.BaseViewModel
import com.zakrodionov.practicalapp.app.core.dispatchers.DispatchersProvider
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.core.onFailure
import com.zakrodionov.practicalapp.app.core.onSuccess
import com.zakrodionov.practicalapp.app.features.posts.PostsScreens.postDetailsScreen
import com.zakrodionov.practicalapp.app.features.posts.domain.PostRepository
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.ArgsPostDetail
import kotlinx.coroutines.Job
import kotlin.random.Random

class PostsViewModel(
    savedStateHandle: SavedStateHandle,
    private val postRepository: PostRepository,
    private val flowRouter: FlowRouter,
    dispatchersProvider: DispatchersProvider,
) : BaseViewModel<PostsState, PostsEvent>(PostsState(), savedStateHandle, dispatchersProvider) {

    private var loadingPostsJob: Job = Job().apply { complete() }

    init {
        if (state.posts == null) loadPosts()
    }

    fun loadPosts(refresh: Boolean = false) {
        if (loadingPostsJob.isCompleted) {
            loadingPostsJob = launchIo {
                if (refresh) update { state.copy(page = 0) }

                if (state.page > 0) {
                    update { it.copy(posts = it.posts?.addLoadingItem()) }
                } else if (!refresh) {
                    update { it.copy(isLoading = true) }
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
                        update {
                            if (refresh) {
                                it.copy(page = 0, posts = emptyList(), error = error)
                            } else {
                                it.copy(error = error)
                            }
                        }
                    }
            }
            update { it.copy(posts = it.posts?.removeLoadingItem(), isLoading = false) }
        }
    }

    fun navigateToPostDetail(post: Post) {
        val id = post.id
        if (id != null) {
            // Для теста разного поведения передаем или сразу весь Post или только id для загрузки поста с сервера
            val postOrNull = if (Random.nextBoolean()) post else null
            flowRouter.navigateTo(postDetailsScreen(ArgsPostDetail(post.id, postOrNull)))
        }
    }
}
