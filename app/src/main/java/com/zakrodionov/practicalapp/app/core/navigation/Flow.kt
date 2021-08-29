package com.zakrodionov.practicalapp.app.core.navigation

import cafe.adriel.voyager.androidx.AndroidScreen

abstract class Flow : AndroidScreen() {
    abstract val title: String
}
