package com.zakrodionov.practicalapp.app.ui

import androidx.annotation.DrawableRes
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import com.zakrodionov.common.ui.models.ResourceString.Res
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.ui.Screens.AboutScreen
import com.zakrodionov.practicalapp.app.ui.Screens.FavoriteScreen
import com.zakrodionov.practicalapp.app.ui.Screens.PostsScreen
import com.zakrodionov.practicalapp.app.ui.about.AboutFragment
import com.zakrodionov.practicalapp.app.ui.postDetails.ArgsPostDetail
import com.zakrodionov.practicalapp.app.ui.postDetails.PostDetailsFragment
import com.zakrodionov.practicalapp.app.ui.posts.PostsFragment
import kotlinx.parcelize.Parcelize

object Screens {
    @Parcelize
    class PostsScreen : AppScreen("PostsScreen") {
        override fun create() = PostsFragment.newInstance()
    }

    @Parcelize
    data class PostDetailScreen(val args: ArgsPostDetail) : AppScreen("PostDetailScreen") {
        override fun create() = PostDetailsFragment.newInstance(args)
    }

    @Parcelize
    class FavoriteScreen : AppScreen("FavoriteScreen") {
        override fun create() = FavoriteFragment()
    }

    @Parcelize
    class AboutScreen : AppScreen("AboutScreen") {
        override fun create() = AboutFragment()
    }

    fun multiStack() = MultiAppScreen("MultiStack", Tab.values().map { it.appScreen }, 0)
}

enum class Tab(val appScreen: AppScreen, val title: Res, @DrawableRes val icon: Int) {
    POSTS(PostsScreen(), Res(R.string.posts), R.drawable.ic_list),
    FAVORITE(FavoriteScreen(), Res(R.string.favorite), R.drawable.ic_favorite),
    ABOUT(AboutScreen(), Res(R.string.about), R.drawable.ic_about)
}
