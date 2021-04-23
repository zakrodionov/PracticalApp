package com.zakrodionov.practicalapp.app.features.posts

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.zakrodionov.practicalapp.app.features.posts.ui.PostsFlowFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.postDetails.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.posts.ui.postDetails.PostDetailsFragment
import com.zakrodionov.practicalapp.app.features.posts.ui.postsList.PostsFragment

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
