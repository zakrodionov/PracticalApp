package com.zakrodionov.practicalapp.app.features.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import com.google.accompanist.insets.ProvideWindowInsets
import com.zakrodionov.common.extensions.disableFitsSystemWindows
import com.zakrodionov.common.extensions.postDelayed
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.features.home.FavoritesTab
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

class RootActivity : ComponentActivity() {

    private val deepLinkScreens = mutableStateOf<List<String>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window?.disableFitsSystemWindows()

        setContent {
            PracticalAppTheme {
                ProvideWindowInsets {
                    RootScreen()
                }
            }
        }

        if (savedInstanceState == null) {
            simulateReceiveDeepLink()
        }
    }

    @Composable
    fun RootScreen() {
        val deepLinkScreens by rememberSaveable { deepLinkScreens }

        if (deepLinkScreens.isEmpty()) {
            RootNavigator(startScreen = HomeScreen())
        } else {
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

    private fun simulateReceiveDeepLink() {
        postDelayed(4000) {
            deepLinkScreens.value = mutableListOf("Open Favorite tab, with nested LoginFlow")
        }
    }
}
