package com.zakrodionov.practicalapp.app.features.posts.ui.detail

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.extensions.capitalizeFirstLetter
import com.zakrodionov.common.extensions.dtfDateTimeFullMonth
import com.zakrodionov.common.extensions.initialArguments
import com.zakrodionov.common.extensions.load
import com.zakrodionov.common.extensions.parseOffsetDateTime
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.common.extensions.toLocaleDateTimeApplyZone
import com.zakrodionov.common.extensions.withInitialArguments
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPostDetailBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import org.koin.core.parameter.parametersOf

class PostDetailsFragment : BaseFragment<PostDetailsState, PostDetailsEvent>(R.layout.fragment_post_detail) {

    companion object {
        fun newInstance(args: ArgsPostDetail) = PostDetailsFragment().withInitialArguments(args)
    }

    override val viewModel: PostDetailViewModel by stateViewModel { parametersOf(initialArguments()) }
    override val binding: FragmentPostDetailBinding by viewBinding(FragmentPostDetailBinding::bind)

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

    // Вообще это надо маппить в data слое или VM в OffsetDateTime, но для примера конвертации даты пока будет здесь
    private fun parsePostDate(date: String?): String? =
        date?.parseOffsetDateTime()?.toLocaleDateTimeApplyZone()?.format(dtfDateTimeFullMonth)
}
