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
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.zakrodionov.common.extensions.capitalizeFirstLetter
import com.zakrodionov.common.extensions.dtfDateTimeFullMonth
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.parseOffsetDateTime
import com.zakrodionov.common.extensions.toLocaleDateTimeApplyZone
import com.zakrodionov.practicalapp.app.ui.components.Lce
import org.koin.androidx.compose.getStateViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PostDetailsScreen(postId: String) {
    val viewModel = getStateViewModel<PostDetailViewModel>(parameters = { parametersOf(postId) })
    val state = viewModel.stateFlow.collectAsState()
    val post = state.value.post

    Lce(lceState = state.value.lceState, tryAgain = { viewModel.loadPostDetails() }) {
        Column(modifier = Modifier.fillMaxSize()) {
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
