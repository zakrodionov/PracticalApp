package com.zakrodionov.practicalapp.app.features.home.posts.list

import androidx.lifecycle.SavedStateHandle
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.EmptyState
import com.zakrodionov.common.ui.lce.ErrorState
import com.zakrodionov.practicalapp.CoroutinesTestExtension
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.domain.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class PostsViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @MockK
    lateinit var postRepository: PostRepository

    lateinit var postsViewModel: PostsViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        postsViewModel = PostsViewModel(
            savedStateHandle = SavedStateHandle(),
            postRepository = postRepository,
            flowRouter = mockk()
        )
    }

    @Test
    fun `verify state when PostRepository returns empty list`() {
        // given
        coEvery { postRepository.getPosts(0) } returns Result.Success(emptyList())

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assert(postsViewModel.state.posts?.isEmpty() ?: false)
        assertEquals(EmptyState, postsViewModel.state.lceState)
    }

    @Test
    fun `verify state when PostRepository returns non empty list`() {
        // given
        coEvery { postRepository.getPosts(0) } returns Result.Success(listOf(mockk(), mockk()))

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assert(postsViewModel.state.posts?.isNotEmpty() ?: false)
        assert(postsViewModel.state.lceState is ContentState)
    }

    @Test
    fun `verify state when PostRepository returns NetworkConnectionError`() {
        // given
        coEvery { postRepository.getPosts(0) } returns Result.Failure(NetworkConnectionError())

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assert(postsViewModel.state.error != null)
        assert(postsViewModel.state.lceState is ErrorState)
    }
}
