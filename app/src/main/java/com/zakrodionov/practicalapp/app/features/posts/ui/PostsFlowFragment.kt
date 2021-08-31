package com.zakrodionov.practicalapp.app.features.posts.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.posts.PostsScreens.postsScreen

class PostsFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
) {

    companion object {
        fun newInstance() = PostsFlowFragment()
    }

    override val startScreen: Screen = postsScreen()
}
