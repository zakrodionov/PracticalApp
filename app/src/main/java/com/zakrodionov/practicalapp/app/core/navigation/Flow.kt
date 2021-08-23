package com.zakrodionov.practicalapp.app.core.navigation

import cafe.adriel.voyager.core.screen.Screen

interface Flow : Screen {
    val title: String
}
