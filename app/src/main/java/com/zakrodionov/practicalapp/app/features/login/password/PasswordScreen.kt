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
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.ui.components.PasswordTextField
import com.zakrodionov.practicalapp.app.ui.components.PrimaryButton
import com.zakrodionov.practicalapp.app.ui.defaultInsetsPadding
import org.koin.androidx.compose.get

class PasswordScreen : BaseScreen() {
    companion object {
        const val KEY = "PasswordScreen"
    }

    override val key: String = KEY

    @Composable
    override fun Content() {
        super.Content()

        val navigator = LocalNavigator.current
        val appPreferences = get<AppPreferences>()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .defaultInsetsPadding()
        ) {
            PasswordTextField(onValueChanged = { debug(it) })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = TextResource.fromStringId(R.string.next),
                onClick = {
                    appPreferences.isLogged = true
                    navigator?.popUntilRoot()
                }
            )
        }
    }
}
