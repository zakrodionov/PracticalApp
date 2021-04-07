package com.zakrodionov.practicalapp.app.ui.postDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.initialArguments
import com.zakrodionov.common.extensions.withInitialArguments
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import com.zakrodionov.practicalapp.app.ui.posts.PostItem
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment : BaseComposeFragment() {

    // Todo koin crash when getting vm with paramaters&stateBundle in composable
    private val viewModel by stateViewModel<PostDetailViewModel> { parametersOf(initialArguments()) }

    companion object {
        fun newInstance(args: ArgsPostDetail) = PostDetailsFragment().withInitialArguments(args)
    }

    @Composable
    override fun ComposeContent() {
        PostScreen(viewModel)
    }
}

@Composable
fun PostScreen(viewModel: PostDetailViewModel) {
    val state = viewModel.stateFlow.collectAsState(null)

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        state.value?.post.ifNotNull { PostItem(it) { } }

        if (state.value?.isLoading == true) CircularProgressIndicator(modifier = Modifier.size(40.dp))
    }
}
