package com.zakrodionov.practicalapp.app.features.posts.ui.list

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.rv.DiffCallback
import com.zakrodionov.common.ui.rv.EndlessScroll
import com.zakrodionov.common.ui.rv.LoadingItem
import com.zakrodionov.common.ui.rv.loadingDelegate
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.NONE
import com.zakrodionov.practicalapp.app.features.posts.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.ui.components.Lce
import com.zakrodionov.practicalapp.app.ui.components.LoadingItem
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)
    override val screenAnimationStrategy: ScreenAnimationStrategy = NONE

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DiffCallback,
            loadingDelegate(),
            postDelegate { viewModel.navigateToPost(it) }
        )
    }

    override fun setupViews(view: View, savedInstanceState: Bundle?) {
        setupRvPosts()
        setupLceLayout()
    }

    private fun setupRvPosts() = with(binding) {
        val layoutManager = LinearLayoutManager(context)
        rvPosts.setup(adapter, layoutManager = layoutManager)
        rvPosts.addOnScrollListener(object : EndlessScroll(layoutManager) {
            override fun onLoadMore() {
                viewModel.loadPosts()
            }
        })

        srlPosts.setOnRefreshListener {
            viewModel.loadPosts(true)
        }
    }

    private fun setupLceLayout() {
        binding.lceLayout.tryAgainButtonClickListener = View.OnClickListener {
            viewModel.loadPosts()
        }
    }

    override fun clearViews() {
        binding.rvPosts.adapter = null
    }

    override fun render(state: PostsState) {
        adapter.items = state.posts
        with(binding) {
            if (!state.isLoading) srlPosts.isRefreshing = false
            srlPosts.isEnabled = state.error == null
            lceLayout.renderState(state.lceState)
        }
    }

    override fun sideEffect(event: PostsEvent) = Unit
}

@Composable
fun PostsScreen(onPostClick: (Post) -> Unit) {
    val viewModel = getStateViewModel<PostsViewModel>()
    val state = viewModel.stateFlow.collectAsState()

    Lce(lceState = state.value.lceState, tryAgain = { viewModel.loadPosts() }) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(state.value.isLoading),
            onRefresh = { viewModel.loadPosts(refresh = true) },
        ) {
            val listState = rememberLazyListState()
            if (listState.layoutInfo.totalItemsCount != 0 && listState.firstVisibleItemIndex + 5 > listState.layoutInfo.totalItemsCount) {
                viewModel.loadPosts()
            }

            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(15.dp), // Расстояние между айтемами
                contentPadding = PaddingValues(20.dp) // Отступы всего LazyColumn
            ) {
                items(state.value.posts.orEmpty(), key = { it.itemId }) { item ->
                    when (item) {
                        is Post -> PostItem(item) { onPostClick(it) }
                        LoadingItem -> LoadingItem()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(post: Post, onClick: (Post) -> Unit) {
    Card(onClick = { onClick(post) }) {
        Column {
            Image(
                painter = rememberImagePainter(post.image.orEmpty()),
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Text(modifier = Modifier.padding(5.dp), text = post.text.orEmpty())
        }
    }
}
