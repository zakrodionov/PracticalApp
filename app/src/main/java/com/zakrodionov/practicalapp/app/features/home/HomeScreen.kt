package com.zakrodionov.practicalapp.app.features.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.ui.theme.BottomBarColor

class HomeScreen : BaseScreen() {

    override fun statusBarColor(): Color = Color.Transparent

    @Composable
    override fun Content() {
        super.Content()

        TabNavigator(PostsTab) {
            Scaffold(
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation(backgroundColor = BottomBarColor) {
                        TabNavigationItem(PostsTab)
                        TabNavigationItem(FavoritesTab)
                        TabNavigationItem(AboutTab)
                    }
                }
            )
        }
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
