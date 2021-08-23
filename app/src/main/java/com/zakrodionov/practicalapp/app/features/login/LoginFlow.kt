package com.zakrodionov.practicalapp.app.features.login

import androidx.compose.runtime.Composable
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import com.zakrodionov.practicalapp.app.ui.components.Flow
import com.zakrodionov.practicalapp.app.ui.components.FlowContent

class LoginFlow : Flow {
    override val title: String = "LoginFlow"

    @Composable
    override fun Content() {
        FlowContent(startScreen = PhoneScreen())
    }
}
