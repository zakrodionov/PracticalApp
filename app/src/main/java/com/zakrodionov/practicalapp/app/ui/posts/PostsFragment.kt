package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.setData
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ScreenState.STUB
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.ui.login.email.EmailView
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import com.zakrodionov.practicalapp.domain.model.Posts
import com.zakrodionov.practicalapp.domain.model.Posts.Post
import dev.chrisbanes.accompanist.glide.GlideImage
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    override fun setupViews(view: View, savedInstanceState: Bundle?) = Unit

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent {
                PostsScreen(viewModel)
            }
        }

    override fun clearViews() = Unit

    override fun sideEffect(event: PostsEvent) {
        when (event) {
            is PostsEvent.ShowEvent -> handleShowAction(event.showAction)
        }
    }

    override fun render(state: PostsState) = Unit
}

@Composable
fun PostsScreen(viewModel: PostsViewModel) {
    val state = viewModel.container.stateFlow.collectAsState(null)

    Box(contentAlignment = Alignment.Center) {
        if (state.value?.posts.isNullOrEmpty()) {
            EmptyStub(text = "EMPTY DATA")
        } else {
            PostList(state.value?.posts) {
                viewModel.navigateToPost(it.id)
            }
        }

        if (state.value?.isLoading == true) CircularProgressIndicator(modifier = Modifier.size(40.dp))
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
                GlideImage(
                    data = post.image.orEmpty(),
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