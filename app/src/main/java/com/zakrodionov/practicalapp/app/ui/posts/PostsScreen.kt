package com.zakrodionov.practicalapp.app.ui.posts

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.glide.rememberGlidePainter
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel

class PostsFragment : BaseComposeFragment() {
    @Composable
    override fun ComposeContent() {
        PostsScreen()
    }
}

@Composable
fun PostsScreen() {
    //val viewModel = getViewModel<PostsViewModel>()
    //val state = viewModel.stateFlow.collectAsState(null)

    Box(contentAlignment = Alignment.Center) {
//        if (state.value?.posts.isNullOrEmpty()) {
//            EmptyStub(text = "EMPTY DATA")
//        } else {
//            PostList(state.value?.posts) {
//                viewModel.navigateToPost(it.id)
//            }
//        }
//
//        if (state.value?.isLoading == true) CircularProgressIndicator(modifier = Modifier.size(40.dp))
    }
}

@Composable
fun PostList(posts: List<Post>?, onItemClick: (Post) -> Unit) {
    MaterialTheme {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 20.dp)
        ) {
            items(posts.orEmpty()) { post ->
                PostItem(post, onItemClick)
            }
        }
    }
}

@Composable
fun PostItem(post: Post, onClick: (Post) -> Unit) {
    Box(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Card(
            modifier = Modifier.clickable { onClick(post) }
        ) {
            Column {
                Image(
                    painter = rememberGlidePainter(post.image.orEmpty()),
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
}

@Composable
fun EmptyStub(text: String) {
    Box(contentAlignment = Alignment.Center) {
        Text(text = text, fontSize = 40.sp)
    }
}

@Composable
@Preview(widthDp = 200)
fun PostListPreview() {
    val previewPosts = listOf(
        Post(
            null,
            "https://img.dummyapi.io/photo-1564694202779-bc908c327862.jpg",
            null,
            null,
            null,
            null,
            null,
            "Test Title"
        )
    )
    PostList(posts = previewPosts) {}
}
