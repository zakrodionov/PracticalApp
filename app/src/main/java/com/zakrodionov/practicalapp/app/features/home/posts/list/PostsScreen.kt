package com.zakrodionov.practicalapp.app.features.home.posts.list

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
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import coil.compose.rememberImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.zakrodionov.common.ui.LoadingItem
import com.zakrodionov.practicalapp.app.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.ui.components.Lce
import com.zakrodionov.practicalapp.app.ui.components.LoadingItem
import com.zakrodionov.practicalapp.app.ui.defaultInsetsPadding
import org.koin.androidx.compose.getStateViewModel
import kotlin.random.Random

const val PAGINATION_THRESHOLD = 5

class PostsScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getStateViewModel<PostsViewModel>()
        val state = viewModel.stateFlow.collectAsState()

        Lce(lceState = state.value.lceState, tryAgain = { viewModel.loadPosts() }) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(state.value.isLoading),
                onRefresh = { viewModel.loadPosts(refresh = true) },
                modifier = Modifier.defaultInsetsPadding()
            ) {
                val listState = rememberLazyListState()
                if (listState.layoutInfo.totalItemsCount != 0 &&
                    listState.firstVisibleItemIndex + PAGINATION_THRESHOLD >
                    listState.layoutInfo.totalItemsCount
                ) {
                    viewModel.loadPosts()
                }

                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(15.dp), // Расстояние между айтемами
                    contentPadding = PaddingValues(20.dp) // Отступы всего LazyColumn
                ) {
                    items(state.value.posts.orEmpty(), key = { it.itemId }) { item ->
                        when (item) {
                            is Post -> PostItem(item) {
                                navigateToPostDetail(navigator, it)
                            }
                            LoadingItem -> LoadingItem()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToPostDetail(navigator: Navigator?, post: Post) {
        val id = post.id
        if (id != null) {
            // Для теста разного поведения передаем или сразу весь Post или только id для загрузки поста с сервера
            val postOrNull = if (Random.nextBoolean()) post else null
            navigator?.push(PostDetailsScreen(ArgsPostDetail(id, postOrNull)))
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
