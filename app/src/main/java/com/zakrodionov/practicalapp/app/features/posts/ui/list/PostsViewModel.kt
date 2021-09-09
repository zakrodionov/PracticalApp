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

    private var loadingPostsJob: Job = Job()

    init {
        if (state.posts == null) loadPosts(initial = true)
    }

    fun loadPosts(refresh: Boolean = false, initial: Boolean = false) {
        if (loadingPostsJob.isCompleted || initial) {
            loadingPostsJob = launchIo {
                if (refresh) reduce { state.copy(page = 0) }

                if (state.page > 0) {
                    reduce { state.copy(posts = state.posts?.addLoadingItem()) }
                } else if (!refresh) {
                    reduce { state.copy(isLoading = true) }
                }

                postRepository
                    .getPosts(state.page)
                    .onSuccess { posts ->
                        val newPosts = if (refresh) posts else state.posts.orEmpty().plus(posts)
                        reduce { state.copy(posts = newPosts, error = null, page = state.increasePage()) }
                    }
                    .onFailure {
                        reduce { state.copy(error = it) }
                    }
                reduce { state.copy(posts = state.posts?.removeLoadingItem(), isLoading = false) }
            }
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
