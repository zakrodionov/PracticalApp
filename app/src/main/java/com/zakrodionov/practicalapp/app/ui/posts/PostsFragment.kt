package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import by.kirich1409.viewbindingdelegate.viewBinding
import com.redmadrobot.lib.sd.base.StateDelegate
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import org.koin.androidx.viewmodel.ext.android.stateViewModel
import timber.log.Timber

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    companion object {
        fun newInstance() = PostsFragment()
    }

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val adapter by lazy {
        PostsAdapter { viewModel.navigateToPost(it.id) }
    }

    // Для упрощения работы с вью на экране где много состояний удобно использовать библиотеку StateDelegate
    // На простых экранах можно обойтись без нее
    private lateinit var screenState: StateDelegate<ScreenState>

    override fun setupViews(view: View, savedInstanceState: Bundle?) = with(binding) {
//        screenState = StateDelegate(
//            State(CONTENT, binding.srlPosts),
//            State(STUB, binding.layoutEmptyStub.root),
//            State(ERROR, binding.layoutError.root),
//        )

        rvPosts.setup(
            adapter.withLoadStateHeaderAndFooter(
                header = PostsLoadStateAdapter { adapter.retry() },
                footer = PostsLoadStateAdapter { adapter.retry() }
            )
        )

        srlPosts.setOnRefreshListener {
            adapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow.collectLatest { loadStates ->
                Timber.d(loadStates.toString())
                binding.srlPosts.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.posts.collectLatest {
                adapter.submitData(it)
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.rvPosts.scrollToPosition(0) }
        }

        Unit
    }

    override fun clearViews() {
        binding.rvPosts.adapter = null
    }

    override fun sideEffect(event: PostsEvent) {
        when (event) {
            is PostsEvent.ShowEvent -> handleShowAction(event.showAction)
        }
    }

    override fun render(state: PostsState)  = Unit
}
