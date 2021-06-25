package com.zakrodionov.practicalapp.app.features.posts

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.posts.ui.PostsFlowFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.posts.ui.detail.PostDetailsFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.list.PostsFragment

@Suppress("FunctionName")
object PostsScreens {
    fun postsFlowScreen() = FragmentScreen("PostsFlowScreen") {
        PostsFlowFragment.newInstance()
    }

    fun postsScreen() = FragmentScreen("PostsScreen") {
        PostsFragment.newInstance()
    }

    fun postDetailsScreen(args: ArgsPostDetail) = FragmentScreen("PostDetailScreen") {
        PostDetailsFragment.newInstance(args)
    }
}
