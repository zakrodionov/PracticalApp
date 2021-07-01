package com.zakrodionov.practicalapp.app.features.posts.ui.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.rv.DiffCallback
import com.zakrodionov.common.ui.rv.EndlessScroll
import com.zakrodionov.common.ui.rv.loadingDelegate
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
        AsyncListDifferDelegationAdapter(
            DiffCallback,
            loadingDelegate(),
            postDelegate { viewModel.navigateToPost(it.id) }
        )
    }

    override fun setupViews(view: View, savedInstanceState: Bundle?) {
        setupRvPosts()
        setupLceLayout()
    }

    private fun setupRvPosts() = with(binding) {
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

    private fun setupLceLayout() {
        binding.lceLayout.tryAgainButtonClickListener = View.OnClickListener {
            viewModel.loadPosts()
        }
    }

    override fun clearViews() {
        binding.rvPosts.adapter = null
    }

    override fun render(state: PostsState) {
        adapter.items = state.posts
        with(binding) {
            if (!state.isLoading) srlPosts.isRefreshing = false
            srlPosts.isEnabled = state.error == null
            lceLayout.renderState(state.lceState)
        }
    }

    override fun sideEffect(event: PostsEvent) = Unit
}
