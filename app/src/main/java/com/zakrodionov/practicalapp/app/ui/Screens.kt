package com.zakrodionov.practicalapp.app.ui

import androidx.annotation.DrawableRes
import com.github.terrakok.modo.android.AppScreen
import com.github.terrakok.modo.android.MultiAppScreen
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.ui.Screens.AboutScreen
import com.zakrodionov.practicalapp.app.ui.Screens.FavoriteScreen
import com.zakrodionov.practicalapp.app.ui.Screens.PostsScreen
import com.zakrodionov.practicalapp.app.ui.about.AboutFragment
import com.zakrodionov.practicalapp.app.ui.login.LoginFlowFragment
import com.zakrodionov.practicalapp.app.ui.login.email.EmailFragment
import com.zakrodionov.practicalapp.app.ui.login.password.PasswordFragment
import com.zakrodionov.practicalapp.app.ui.postDetails.ArgsPostDetail
import com.zakrodionov.practicalapp.app.ui.postDetails.PostDetailsFragment
import com.zakrodionov.practicalapp.app.ui.posts.PostsFragment
import kotlinx.parcelize.Parcelize

object Screens {
    @Parcelize
    class PostsScreen : AppScreen("PostsScreen") {
        override fun create() = PostsFragment()
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

    @Parcelize
    class LoginFlowScreen : AppScreen("LoginFlowScreen") {
        override fun create() = LoginFlowFragment()
    }

    @Parcelize
    class EmailScreen : AppScreen("EmailScreen") {
        override fun create() = EmailFragment()
    }

    @Parcelize
    class PasswordScreen : AppScreen("PasswordScreen") {
        override fun create() = PasswordFragment()
    }

    fun multiStack() = MultiAppScreen(
        "MultiStack",
        Tab.values().map { it.appScreen }, // root screens in tabs
        0
    )
}

enum class Tab(val appScreen: AppScreen, val title: String, @DrawableRes val icon: Int) {
    POSTS(PostsScreen(), "Posts", R.drawable.ic_list),
    FAVORITE(FavoriteScreen(), "Favorite", R.drawable.ic_favorite),
    ABOUT(AboutScreen(), "About", R.drawable.ic_about)
}
