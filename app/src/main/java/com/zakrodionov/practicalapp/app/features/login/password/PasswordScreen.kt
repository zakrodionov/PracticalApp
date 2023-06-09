package com.zakrodionov.practicalapp.app.features.login.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.core.ui.components.PasswordTextField
import com.zakrodionov.practicalapp.app.core.ui.components.PrimaryButton
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.home.HomeScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import kotlinx.parcelize.Parcelize
import org.koin.compose.koinInject
@Parcelize
class PasswordScreen : BaseScreen() {
    @Composable
    override fun Content() {
        super.Content()

        val globalNavigator = LocalGlobalNavigator.current
        val navigator = LocalNavigator.currentOrThrow
        val appPreferences = koinInject<AppPreferences>()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            PasswordTextField(onValueChanged = { debug(it) })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = TextResource.fromStringId(R.string.next),
                onClick = {
                    loginAndNavigate(
                        appPreferences = appPreferences,
                        globalNavigator = globalNavigator,
                        navigator = navigator
                    )
                }
            )
        }
    }

    private fun loginAndNavigate(
        appPreferences: AppPreferences,
        globalNavigator: Navigator,
        navigator: Navigator
    ) {
        appPreferences.isLogged = true

        if ((navigator.parent?.lastItem as? LoginFlow)?.fromLaunchScreen != false) {
            globalNavigator.replaceAll(HomeScreen())
        } else {
            navigator.parent?.pop()
        }
    }
}
