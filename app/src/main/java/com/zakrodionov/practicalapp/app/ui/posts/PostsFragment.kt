package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.zakrodionov.common.extensions.hide
import com.zakrodionov.common.extensions.hideIf
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.extensions.show
import com.zakrodionov.common.extensions.showIf
import com.zakrodionov.common.ui.rv.DiffCallback
import com.zakrodionov.common.ui.rv.EndlessScroll
import com.zakrodionov.common.ui.rv.loadingDelegate
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.ScreenState.CONTENT
import com.zakrodionov.practicalapp.app.core.ScreenState.ERROR
import com.zakrodionov.practicalapp.app.core.ScreenState.STUB
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val adapter by lazy {
        AsyncListDifferDelegationAdapter(
            DiffCallback,
            loadingDelegate(),
            postDelegate { viewModel.navigateToPost(it.id) }
        )
    }

    override fun setupViews(view: View, savedInstanceState: Bundle?) = with(binding) {
        setupPostRv()

        layoutError.btnTryAgain.setOnClickListener {
            viewModel.loadPosts()
        }
    }

    private fun setupPostRv() = with(binding) {
        val layoutManager = LinearLayoutManager(context)
        rvPosts.setup(adapter, layoutManager = layoutManager)
        rvPosts.addOnScrollListener(object : EndlessScroll(layoutManager) {
            override fun onLoadMore() {
                viewModel.loadPosts()
            }
        })

        srlPosts.setOnRefreshListener {
            viewModel.loadPosts(true)
        }
    }

    override fun clearViews() {
        binding.rvPosts.adapter = null
    }

    override fun sideEffect(event: PostsEvent) = Unit

    override fun render(state: PostsState) {
        adapter.items = state.posts
        with(binding) {
            when {
                state.isLoading && srlPosts.isRefreshing -> return
                state.isLoading -> progressBar.show()
                else -> {
                    srlPosts.isRefreshing = false
                    progressBar.hide()
                }
            }

            state.error.ifNotNull { error ->
                layoutError.tvTitle.text = error.message.getText(requireContext())
            }

            rvPosts.showIf { state.screenState == CONTENT }
            srlPosts.hideIf { state.screenState == ERROR }
            layoutError.root.showIf { state.screenState == ERROR }
            layoutEmptyStub.root.showIf { state.screenState == STUB }
        }
    }
}
