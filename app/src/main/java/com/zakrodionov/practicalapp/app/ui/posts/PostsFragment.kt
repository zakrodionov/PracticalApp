package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.binding.listeners.addClickListener
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import com.zakrodionov.common.extensions.ifNotNull
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ScreenState.STUB
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import com.zakrodionov.practicalapp.databinding.ItemPostBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val adapter by lazy {
        FastItemAdapter<PostItem>()
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

        srlPosts.setOnRefreshListener {
            viewModel.loadPosts()
        }

        layoutError.btnTryAgain.setOnClickListener {
            viewModel.loadPosts()
        }
    }

    private fun setupPostRv() = with(binding) {
        rvPosts.setup(adapter)
        adapter.addClickListener<ItemPostBinding, PostItem>({ it.root }) { _, _, _, item ->
            viewModel.navigateToPost(item.post.id)
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
        FastAdapterDiffUtil[adapter.itemAdapter] = state.posts?.map { PostItem(it) }.orEmpty()

        with(binding) {
            if (srlPosts.isRefreshing) {
                srlPosts.isRefreshing = state.isLoading
            } else {
                progressBar.isVisible = state.isLoading
            }

            state.error.ifNotNull { error ->
                layoutError.tvTitle.text = error.message.getText(requireContext())
            }
        }
        screenState.currentState = state.screenState
    }
}
