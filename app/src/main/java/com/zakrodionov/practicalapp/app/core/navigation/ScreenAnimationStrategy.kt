package com.zakrodionov.practicalapp.app.core.navigation

interface AnimationScreen {
    val screenAnimationStrategy: ScreenAnimationStrategy
}

enum class ScreenAnimationStrategy {
    SLIDE_HORIZONTAL,
    SLIDE_VERTICAL,
    NONE
}
