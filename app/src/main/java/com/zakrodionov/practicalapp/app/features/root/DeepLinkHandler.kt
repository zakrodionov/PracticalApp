package com.zakrodionov.practicalapp.app.features.root

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.tab.Tab
import com.squareup.moshi.JsonClass
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.home.AboutTab
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.home.PostsTab
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.home.posts.list.PostsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import kotlinx.parcelize.Parcelize
import org.koin.androidx.compose.get

@Parcelize
@JsonClass(generateAdapter = true)
data class DeepLinkNavigation(
    val tab: NavigationScreen = NavigationScreen(),
    val flow: NavigationScreen = NavigationScreen(),
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
    val screens: List<NavigationScreen> = emptyList(),
) : Parcelable

object DeepLinkHandler {

    @Composable
    fun HandleDeepLink(deepLinkNavigation: DeepLinkNavigation) {
        val appPreferences = get<AppPreferences>()
        val skipLoginScreen = appPreferences.isLogged || appPreferences.isSkipLoginFlow

        val startScreen = if (skipLoginScreen) HomeScreen() else LoginFlow(true)
        Navigator(screens = listOf(startScreen)) { navigator ->
            // Root aka global navigator
            CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
                CurrentScreen()

                if (!deepLinkNavigation.isEmpty && !skipLoginScreen) {
                    navigator.replaceAll(deepLinkNavigation.parseNavigation())
                }
            }
        }
    }

    private fun DeepLinkNavigation.parseNavigation(): List<Screen> {
        val tabInnerScreens = tab.screens.mapNotNull { it.getScreen() }
        val tab = getTab(tabInnerScreens)
        val flowInnerScreens = flow.screens.mapNotNull { it.getScreen() }
        val flow = flow.getScreen(flowInnerScreens)
        return flow?.let { listOf(HomeScreen(tab)).plus(it) } ?: listOf(HomeScreen(tab))
    }

    private fun NavigationScreen.getScreen(innerScreens: List<Screen> = emptyList()): Screen? = when (name) {
        "flow_login" -> LoginFlow(false, innerScreens)
        "screen_phone" -> PhoneScreen()
        "screen_posts" -> PostsScreen()
        "screen_post_detail" -> PostDetailsScreen(ArgsPostDetail(argument))
        else -> null
    }

    private fun DeepLinkNavigation.getTab(innerScreens: List<Screen> = emptyList()): Tab = when (tab.name) {
        "tab_posts" -> innerScreens.takeIf { it.isNotEmpty() }?.let { PostsTab(it) } ?: PostsTab()
        "tab_favorites" -> innerScreens.takeIf { it.isNotEmpty() }?.let { FavoritesTab(it) } ?: FavoritesTab()
        "tab_about" -> innerScreens.takeIf { it.isNotEmpty() }?.let { AboutTab(it) } ?: AboutTab()
        else -> innerScreens.takeIf { it.isNotEmpty() }?.let { PostsTab(it) } ?: PostsTab()
    }
}
