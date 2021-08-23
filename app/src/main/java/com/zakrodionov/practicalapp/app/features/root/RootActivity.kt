package com.zakrodionov.practicalapp.app.features.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.app.core.navigation.CurrentScreen
import com.zakrodionov.practicalapp.app.features.home.AboutTab
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.PostsTab
import com.zakrodionov.practicalapp.app.features.login.password.PasswordScreen
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticalAppTheme {
                Content()
            }
        }
    }

    @Composable
    fun Content() {
        val currentScreen = CurrentScreen.current.collectAsState()

        debug("vdcv ${currentScreen.value}")
        TabNavigator(PostsTab) { tabNavigator ->
            Scaffold(
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        debug("CurrentTab")
                        CurrentTab()
                    }
                },
                bottomBar = {
                    if (shouldShowBottomBar(currentScreen.value)) {
                        BottomNavigation {
                            TabNavigationItem(PostsTab)
                            TabNavigationItem(FavoritesTab)
                            TabNavigationItem(AboutTab)
                        }
                    }
                }
            )
        }
    }

    @Composable
    fun shouldShowBottomBar(currentScreen: Screen?): Boolean {
        if (currentScreen == null) return true
        // Сюда добавляем экраны в которых надо скрывать BottomBar
        val routesWithoutBottomBar = listOf(
            PhoneScreen.KEY,
            PasswordScreen.KEY,
        )
        return !routesWithoutBottomBar.contains(currentScreen.key)
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current.key == tab.key,
            onClick = { tabNavigator.current = tab },
            icon = { Icon(painter = tab.options.icon!!, contentDescription = tab.options.title) }
        )
    }
}
