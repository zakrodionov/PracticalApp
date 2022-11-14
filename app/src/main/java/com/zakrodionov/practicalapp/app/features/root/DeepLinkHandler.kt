package com.zakrodionov.practicalapp.app.features.root

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow

object DeepLinkHandler {

    @Composable
    fun handleDeepLink(deepLinkScreens: List<String>) {
        if (deepLinkScreens.isEmpty()) {
            RootNavigator(startScreen = HomeScreen())
        } else {
            // TODO
            RootNavigator(startScreen = HomeScreen(FavoritesTab(listOf(LoginFlow()))))
        }
    }

    @Composable
    private fun RootNavigator(startScreen: Screen) =
        Navigator(screen = startScreen) { navigator ->
            // Root aka global navigator
            CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
                CurrentScreen()
            }
        }

    private fun String.getScreen(): Screen? = when (this) {
        "tab_favorites" -> FavoritesTab()
        "flow_login" -> LoginFlow()
        "screen_posts" -> PostsScreen()
        "screen_post_detail" -> PostDetailsScreen(ArgsPostDetail(""))
        else -> null
    }
}
