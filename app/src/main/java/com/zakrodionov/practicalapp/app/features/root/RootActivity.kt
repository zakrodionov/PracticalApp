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
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import com.zakrodionov.practicalapp.app.features.home.AboutTab
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.PostsTab
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
//        val navigator = LocalNavigator.current
//        val tabNavigator = LocalTabNavigator.current
//        debug(navigator?.items?.toString() ?: "null")
//        debug("tab: " + tabNavigator?.current?.toString() ?: "null")

        TabNavigator(PostsTab) { tabNavigator ->
            Scaffold(
                content = {
                    Box(modifier = Modifier.padding(it)) {
                        CurrentTab()
                    }
                },
                bottomBar = {
                    BottomNavigation {
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
