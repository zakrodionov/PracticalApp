package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import com.zakrodionov.common.extensions.OnLaunched
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.app.core.navigation.Flow

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Flow.FlowContent(startScreen: Screen) {
    OnLaunched(
        block = { debug("Navigator - Start flow $title") },
        onDispose = { debug("Navigator - Dispose flow $title") }
    )

    Navigator(screen = startScreen)
}
