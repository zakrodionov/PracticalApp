package com.zakrodionov.practicalapp.app.features.posts.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseTabFragment
import com.zakrodionov.practicalapp.app.features.posts.PostsScreens.postsScreen
import com.zakrodionov.practicalapp.app.features.posts.di.POSTS_QUALIFIER

class PostsFlowFragment : BaseTabFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
    POSTS_QUALIFIER
) {

    companion object {
        fun newInstance() = PostsFlowFragment()
    }

    override val startScreen: Screen = postsScreen()
}
