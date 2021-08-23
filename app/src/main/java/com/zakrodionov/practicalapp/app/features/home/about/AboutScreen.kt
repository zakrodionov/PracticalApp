package com.zakrodionov.practicalapp.app.features.home.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zakrodionov.common.extensions.Subscribe
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredButton
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredText
import com.zakrodionov.practicalapp.app.ui.components.CommonFillSpacer
import com.zakrodionov.practicalapp.app.ui.components.CommonSpacer
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.getStateViewModel

class AboutScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val viewModel = getStateViewModel<AboutViewModel>()
        val state = viewModel.stateFlow.collectAsState()

        Subscribe {
            viewModel.eventFlow.collect {
                when (it) {
                    NavigateToLoginFlow -> navigator?.push(LoginFlow())
                }
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            val title = if (state.value.isLogged) stringResource(R.string.logout) else stringResource(R.string.login)
            CommonSpacer()
            CommonCenteredButton(text = title, onClick = { viewModel.loginOrLogout() })
            CommonFillSpacer()
            CommonCenteredText(stringResource(R.string.app_name) + " - " + BuildConfig.VERSION_NAME)
        }
    }
}
