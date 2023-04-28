@file:Suppress("MagicNumber")

package com.zakrodionov.practicalapp.app.core.ui.theme

import androidx.compose.ui.graphics.Color
import kotlin.random.Random

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val StatusBarColor = Color(0xFFA8DADC)
val BottomBarColor = Color(0xFFA8DADC)

val RandomColor
    get() = Color(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
