package com.zakrodionov.practicalapp.app.features.root

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.google.accompanist.insets.ProvideWindowInsets
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.common.extensions.disableFitsSystemWindows
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

class RootActivity : ComponentActivity() {

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
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun RootScreen() {
        Navigator(screen = HomeScreen()) { navigator ->
            // Root aka global navigator
            CompositionLocalProvider(LocalGlobalNavigator provides navigator) {
                SlideTransition(navigator) { screen ->
                    Column {
                        screen.Content()
                        debug("Navigator - Last Event: ${navigator.lastEvent}")
                    }
                }
            }
        }
    }
}
