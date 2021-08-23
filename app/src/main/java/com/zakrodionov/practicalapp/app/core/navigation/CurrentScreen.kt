package com.zakrodionov.practicalapp.app.core.navigation

import cafe.adriel.voyager.core.screen.Screen
import kotlinx.coroutines.flow.MutableStateFlow

object CurrentScreen {
    val current = MutableStateFlow<Screen?>(null)
}
