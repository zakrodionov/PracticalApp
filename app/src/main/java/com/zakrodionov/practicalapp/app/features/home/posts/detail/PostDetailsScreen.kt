package com.zakrodionov.practicalapp.app.features.home.posts.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.insets.imePadding
import com.zakrodionov.common.extensions.capitalizeFirstLetter
import com.zakrodionov.common.extensions.dtfDateTimeFullMonth
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.parseOffsetDateTime
import com.zakrodionov.common.extensions.toLocaleDateTimeApplyZone
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.LceState
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.domain.model.Posts.Post
import com.zakrodionov.practicalapp.app.ui.components.Lce
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

data class PostDetailsScreen(
    private val args: ArgsPostDetail,
) : BaseScreen() {

    override fun statusBarColor(): Color = Color.Transparent

    @Composable
    override fun Content() {
        super.Content()

        val viewModel = getViewModel<PostDetailViewModel> { parametersOf(args) }
        val state by viewModel.stateFlow.collectAsState()

        PostDetailsScreen(
            lceState = state.lceState,
            post = state.post,
            tryAgain = { viewModel.loadPostDetails() }
        )
    }
}

// Stateless Screen
@OptIn(ExperimentalCoilApi::class)
@Composable
private fun PostDetailsScreen(lceState: LceState, post: Post?, tryAgain: () -> Unit) {
    Lce(lceState = lceState, tryAgain = tryAgain) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            Image(
                painter = rememberImagePainter(post?.image.orEmpty()),
                contentDescription = "Post Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )

            post?.text?.capitalizeFirstLetter().ifNotNull {
                Text(modifier = Modifier.padding(5.dp), text = it)
            }

            post?.publishDate?.parsePostDate()?.ifNotNull {
                Text(modifier = Modifier.padding(5.dp), text = it)
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Todo Footer", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        }
    }
}

// Вообще это надо маппить в data слое или VM в OffsetDateTime, но для примера конвертации даты пока будет здесь
private fun String.parsePostDate(): String? =
    parseOffsetDateTime().toLocaleDateTimeApplyZone().format(dtfDateTimeFullMonth)

@Preview
@Composable
fun PreviewPostDetailsScreen() {
    val post = Post(
        image = "https://img.dummyapi.io/photo-1564694202779-bc908c327862.jpg",
        text = "adult Labrador retriever",
        publishDate = "2020-05-24T14:53:17.598Z"
    )
    PostDetailsScreen(lceState = ContentState, post = post) {}
}
