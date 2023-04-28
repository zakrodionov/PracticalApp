package com.zakrodionov.practicalapp.app.features.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zakrodionov.practicalapp.app.core.navigation.Flow
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.core.navigation.popRoot
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import com.zakrodionov.practicalapp.app.core.ui.components.FlowContent
import com.zakrodionov.practicalapp.app.core.ui.theme.Purple500
import org.koin.compose.koinInject

class LoginFlow(
    val fromLaunchScreen: Boolean,
    private val innerScreens: List<Screen> = listOf(PhoneScreen())
) : Flow() {
    override val title: String = "LoginFlow"

    @Composable
    override fun Content() {
        val appPreferences = koinInject<AppPreferences>()
        val globalNavigator = LocalGlobalNavigator.current
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Skip",
                    color = Purple500,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .wrapContentWidth()
                        .systemBarsPadding()
                        .padding(horizontal = 5.dp)
                        .clickable {
                            closeLoginFlow(
                                appPreferences = appPreferences,
                                globalNavigator = globalNavigator,
                                navigator = navigator
                            )
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                )
            }
            FlowContent(startScreen = innerScreens.toTypedArray())
        }
    }

    private fun closeLoginFlow(
        appPreferences: AppPreferences,
        globalNavigator: Navigator,
        navigator: Navigator
    ) {
        appPreferences.isSkipLoginFlow = true
        if (fromLaunchScreen) {
            globalNavigator.replaceAll(HomeScreen())
        } else {
            navigator.popRoot()
        }
    }
}
