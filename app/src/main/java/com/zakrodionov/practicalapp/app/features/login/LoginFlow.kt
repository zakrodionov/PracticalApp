package com.zakrodionov.practicalapp.app.features.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.zakrodionov.practicalapp.app.core.navigation.Flow
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import com.zakrodionov.practicalapp.app.ui.components.FlowContent

class LoginFlow(private val innerScreens: List<Screen> = listOf(PhoneScreen())) : Flow() {
    override val title: String = "LoginFlow"

    @Composable
    override fun Content() {
        FlowContent(startScreen = innerScreens.toTypedArray())
    }
}
