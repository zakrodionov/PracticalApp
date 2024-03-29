package com.zakrodionov.practicalapp.app.features.home.posts.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.zakrodionov.common.ui.EndlessScrollListener
import com.zakrodionov.common.ui.LoadingItem
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.core.ui.components.Lce
import com.zakrodionov.practicalapp.app.core.ui.components.LoadingItem
import com.zakrodionov.practicalapp.app.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.getViewModel
import kotlin.random.Random
@Parcelize
class PostsScreen : BaseScreen() {

    override fun statusBarColor(): Color = Color.Transparent

    @IgnoredOnParcel
    override val useDarkIconsInStatusBar: Boolean = true

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        super.Content()

        val navigator = LocalNavigator.currentOrThrow
        val viewModel = getViewModel<PostsViewModel>()
        val state by viewModel.stateFlow.collectAsState()

        Lce(lceState = state.lceState, tryAgain = { viewModel.loadPosts() }) {
            val pullRefreshState = rememberPullRefreshState(state.loading.fromSwipeRefresh, {
                viewModel.loadPosts(refresh = true)
            })

            Box(Modifier.pullRefresh(pullRefreshState)) {
                val listState = rememberLazyListState()
                EndlessScrollListener(listState = listState) {
                    viewModel.loadPosts()
                }

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(15.dp), // Расстояние между айтемами
                    contentPadding = PaddingValues(all = 20.dp) // Отступы всего LazyColumn
                ) {
                    item {
                        Spacer(modifier = Modifier.windowInsetsTopHeight(WindowInsets.safeContent))
                    }
                    items(state.posts.orEmpty(), key = { it.itemId }) { item ->
                        when (item) {
                            is Post -> PostItem(item) {
                                navigateToPostDetail(navigator, it)
                            }
                            LoadingItem -> LoadingItem()
                        }
                    }
                }

                PullRefreshIndicator(
                    state.loading.fromSwipeRefresh,
                    pullRefreshState,
                    Modifier.align(Alignment.TopCenter)
                )
            }
        }
    }

    private fun navigateToPostDetail(navigator: Navigator, post: Post) {
        val id = post.id
        if (id != null) {
            // Для теста разного поведения передаем или сразу весь Post или только id для загрузки поста с сервера
            val postOrNull = if (Random.nextBoolean()) post else null
            navigator.push(PostDetailsScreen(ArgsPostDetail(id, postOrNull)))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PostItem(post: Post, onClick: (Post) -> Unit) {
    Card(onClick = { onClick(post) }) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(post.image.orEmpty()),
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
