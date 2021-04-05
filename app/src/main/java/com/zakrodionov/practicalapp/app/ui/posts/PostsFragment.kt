package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import com.zakrodionov.common.extensions.hide
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.extensions.show
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ScreenState.STUB
import com.zakrodionov.common.ui.rv.AsyncListDifferProgressAdapter
import com.zakrodionov.common.ui.rv.EndlessScroll
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val adapter by lazy {
        AsyncListDifferProgressAdapter(postDelegate { viewModel.navigateToPost(it.id) })
    }

    // Для упрощения работы с вью на экране где много состояний удобно использовать библиотеку StateDelegate
    // На простых экранах можно обойтись без нее
    private lateinit var screenState: StateDelegate<ScreenState>

    override fun setupViews(view: View, savedInstanceState: Bundle?) = with(binding) {
        screenState = StateDelegate(
            State(CONTENT, binding.srlPosts),
            State(STUB, binding.layoutEmptyStub.root),
            State(ERROR, binding.layoutError.root),
        )

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

    override fun sideEffect(event: PostsEvent) {
        when (event) {
            is PostsEvent.ShowEvent -> handleShowAction(event.showAction)
        }
    }

    override fun render(state: PostsState) {
        adapter.items = state.posts
        with(binding) {
            if (state.isLoading) {
                if (srlPosts.isRefreshing) return
                if (state.page > 0) adapter.showLoading() else progressBar.show()
            }

            if (!state.isLoading) {
                srlPosts.isRefreshing = false
                progressBar.hide()
            }

            state.error.ifNotNull { error ->
                layoutError.tvTitle.text = error.message.getText(requireContext())
            }
        }
        screenState.currentState = state.screenState
    }
}
