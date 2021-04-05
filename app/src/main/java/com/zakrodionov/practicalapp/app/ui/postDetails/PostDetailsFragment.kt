package com.zakrodionov.practicalapp.app.ui.postDetails

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.initialArguments
import com.zakrodionov.common.extensions.load
import com.zakrodionov.common.extensions.setTextOrHide
import com.zakrodionov.common.extensions.withInitialArguments
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
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

    override fun setupViews(view: View, savedInstanceState: Bundle?) = with(binding) {
        layoutError.btnTryAgain.setOnClickListener {
            viewModel.loadPostDetails()
        }
    }

    override fun sideEffect(event: PostDetailsEvent) {
        when (event) {
            is PostDetailsEvent.ShowEvent -> handleShowAction(event.showAction)
        }
    }

    override fun render(state: PostDetailsState) {
        with(binding) {
            tvTitle.setTextOrHide(state.post?.text)
            ivPhoto.load(state.post?.image)
            progressBar.isVisible = state.isLoading

            state.error.ifNotNull { error ->
                layoutError.tvTitle.text = error.message.getText(requireContext())
            }

            llContent.isVisible = state.screenState == CONTENT
            layoutError.root.isVisible = state.screenState == ERROR
        }
    }
}
