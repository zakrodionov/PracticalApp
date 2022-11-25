package com.zakrodionov.practicalapp.app.features.root

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.squareup.moshi.JsonClass
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class DeepLinkNavigation(
    val feature: NavigationScreen = NavigationScreen(),
    val screens: List<NavigationScreen> = emptyList(),
) : Parcelable {

    companion object {
        val empty = DeepLinkNavigation()
    }

    val isEmpty: Boolean
        get() = this == empty
}

@Parcelize
@JsonClass(generateAdapter = true)
data class NavigationScreen(
    val name: String = "",
    val argument: String = "",
) : Parcelable

object DeepLinkHandler {

    @Composable
    fun HandleDeepLink(deepLinkNavigation: DeepLinkNavigation) {
        if (deepLinkNavigation.isEmpty) {
            RootNavigator(startScreens = listOf(HomeScreen()))
        } else {

            RootNavigator(startScreens = deepLinkNavigation.parseNavigation())
        }
    }

    @Composable
    private fun RootNavigator(startScreens: List<Screen>) =
        Navigator(screens = startScreens) { navigator ->
            // Root aka global navigator
            CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
                CurrentScreen()
            }
        }

    private fun DeepLinkNavigation.parseNavigation(): List<Screen> {
        val innerScreens = screens.mapNotNull { it.getScreen() }
        return feature.getScreen(innerScreens)?.let { listOf(HomeScreen()).plus(it) } ?: listOf(HomeScreen())
    }

    private fun NavigationScreen.getScreen(innerScreens: List<Screen> = emptyList()): Screen? = when (name) {
        "flow_favorites" -> FavoritesTab(innerScreens)
        "flow_login" -> LoginFlow(innerScreens)
        "screen_phone" -> PhoneScreen()
        "screen_posts" -> PostsScreen()
        "screen_post_detail" -> PostDetailsScreen(ArgsPostDetail(argument))
        else -> null
    }
}
