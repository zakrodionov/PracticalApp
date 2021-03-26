package com.zakrodionov.practicalapp.app.ui.posts

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.redmadrobot.lib.sd.base.State
import com.redmadrobot.lib.sd.base.StateDelegate
import com.zakrodionov.common.extensions.setData
import com.zakrodionov.common.extensions.setup
import com.zakrodionov.common.ui.ScreenState
import com.zakrodionov.common.ui.ScreenState.CONTENT
import com.zakrodionov.common.ui.ScreenState.ERROR
import com.zakrodionov.common.ui.ScreenState.STUB
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPostsBinding
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PostsFragment : BaseFragment<PostsState, PostsEvent>(R.layout.fragment_posts) {

    override val viewModel: PostsViewModel by stateViewModel()
    override val binding: FragmentPostsBinding by viewBinding(FragmentPostsBinding::bind)

    private val adapter by lazy {
        ListDelegationAdapter(postDelegate { viewModel.navigateToPost(it.id) })
    }

    // Для упрощения работы с вью на экране где много состояний удобно использовать библиотеку StateDelegate
    // На простых экранах можно обойтись без нее
    private lateinit var screenState: StateDelegate<ScreenState>

    override fun setupViews(view: View, savedInstanceState: Bundle?) = with(binding) {
        screenState = StateDelegate(
            State(CONTENT, binding.rvPosts),
            State(STUB, binding.layoutEmptyStub.root),
            State(ERROR, binding.layoutError.root),
        )
        rvPosts.setup(adapter)
        layoutError.btnTryAgain.setOnClickListener {
            viewModel.loadPosts()
        }
    }

    override fun sideEffect(event: PostsEvent) {
        when (event) {
            is PostsEvent.ShowEvent -> handleShowAction(event.showAction)
        }
    }

    override fun render(state: PostsState) {
        adapter.setData(state.posts)
        binding.progressBar.isVisible = state.isLoading
        screenState.currentState = state.screenState
    }
}