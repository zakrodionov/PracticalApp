package com.zakrodionov.practicalapp.app.features.posts.ui.detail

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import cafe.adriel.voyager.core.screen.Screen
import coil.compose.rememberImagePainter
import com.zakrodionov.common.extensions.capitalizeFirstLetter
import com.zakrodionov.common.extensions.dtfDateTimeFullMonth
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.initialArguments
import com.zakrodionov.common.extensions.load
import com.zakrodionov.common.extensions.parseOffsetDateTime
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.common.extensions.toLocaleDateTimeApplyZone
import com.zakrodionov.common.extensions.withInitialArguments
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.ui.components.Lce
import com.zakrodionov.practicalapp.databinding.FragmentPostDetailBinding
import dev.chrisbanes.insetter.applyInsetter
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment :
    BaseFragment<PostDetailsState, PostDetailsEvent>(R.layout.fragment_post_detail) {

    companion object {
        fun newInstance(args: ArgsPostDetail) = PostDetailsFragment().withInitialArguments(args)
    }

    override val viewModel: PostDetailViewModel by stateViewModel { parametersOf(initialArguments()) }
    override val binding: FragmentPostDetailBinding by viewBinding(FragmentPostDetailBinding::bind)
    override val statusBarColor: Int = R.color.transparent

    override fun setupViews(view: View, savedInstanceState: Bundle?) {
        setupLceLayout()
    }

    private fun setupLceLayout() {
        binding.lceLayout.tryAgainButtonClickListener = View.OnClickListener {
            viewModel.loadPostDetails()
        }
    }

    override fun render(state: PostDetailsState) {
        with(binding) {
            tvTitle.setTextOrHide(state.post?.text?.capitalizeFirstLetter())
            tvDate.setTextOrHide(parsePostDate(state.post?.publishDate))
            ivPhoto.load(state.post?.image)
            lceLayout.renderState(state.lceState)
        }
    }

    override fun sideEffect(event: PostDetailsEvent) = Unit

    // Скрываем статусбар
    override fun applyInsets() {
        view?.applyInsetter {
            type(ime = true) {
                margin()
            }
        }
    }

    // Вообще это надо маппить в data слое или VM в OffsetDateTime, но для примера конвертации даты пока будет здесь
    private fun parsePostDate(date: String?): String? =
        date?.parseOffsetDateTime()?.toLocaleDateTimeApplyZone()?.format(dtfDateTimeFullMonth)
}

data class PostDetailsScreen(val args: ArgsPostDetail) : Screen {
    override val key: String = "PostDetailsScreen"

    @Composable
    override fun Content() {
        val viewModel = getStateViewModel<PostDetailViewModel>(parameters = { parametersOf(args) })
        val state = viewModel.stateFlow.collectAsState()
        val post = state.value.post

        Lce(lceState = state.value.lceState, tryAgain = { viewModel.loadPostDetails() }) {
            Column {
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
            }
        }
    }

    // Вообще это надо маппить в data слое или VM в OffsetDateTime, но для примера конвертации даты пока будет здесь
    private fun String.parsePostDate(): String? =
        parseOffsetDateTime().toLocaleDateTimeApplyZone().format(dtfDateTimeFullMonth)
}
