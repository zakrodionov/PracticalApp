package com.zakrodionov.practicalapp.app.features.home.posts.list

import androidx.lifecycle.SavedStateHandle
import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEmpty
import assertk.assertions.isNotNull
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.EmptyState
import com.zakrodionov.common.ui.lce.ErrorState
import com.zakrodionov.practicalapp.CoroutinesTestExtension
import com.zakrodionov.practicalapp.InstantTaskExecutorExtension
import com.zakrodionov.practicalapp.app.core.NetworkConnectionError
import com.zakrodionov.practicalapp.app.core.Result
import com.zakrodionov.practicalapp.app.domain.repositories.PostRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.RegisterExtension

@ExperimentalCoroutinesApi
class PostsViewModelTest {

    @JvmField
    @RegisterExtension
    val coroutinesTestExtension = CoroutinesTestExtension()

    @JvmField
    @RegisterExtension
    val instantTaskExecutorExtension = InstantTaskExecutorExtension()

    @MockK(relaxed = true)
    lateinit var postRepository: PostRepository

    lateinit var postsViewModel: PostsViewModel

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)

        postsViewModel = PostsViewModel(
            savedStateHandle = SavedStateHandle(),
            postRepository = postRepository,
            dispatchersProvider = coroutinesTestExtension.testDispatchersProvider
        )
    }

    @Test
    fun `verify state when PostRepository returns empty list`() {
        // given
        coEvery { postRepository.getPosts(any()) } returns Result.Success(emptyList())

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assertThat(postsViewModel.state.posts).isNotNull().isEmpty()
        assertThat(postsViewModel.state.lceState).isEqualTo(EmptyState)
    }

    @Test
    fun `verify state when PostRepository returns non empty list`() {
        // given
        coEvery { postRepository.getPosts(any()) } returns Result.Success(listOf(mockk(), mockk()))

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assertThat(postsViewModel.state.posts).isNotNull().isNotEmpty()
        assertThat(postsViewModel.state.lceState).isInstanceOf(ContentState::class)
    }

    @Test
    fun `verify state when PostRepository returns NetworkConnectionError`() {
        // given
        coEvery { postRepository.getPosts(any()) } returns Result.Failure(NetworkConnectionError)

        // when
        postsViewModel.loadPosts(initial = true)

        // then
        assertThat(postsViewModel.state.error).isNotNull()
        assertThat(postsViewModel.state.lceState).isInstanceOf(ErrorState::class)
    }
}
