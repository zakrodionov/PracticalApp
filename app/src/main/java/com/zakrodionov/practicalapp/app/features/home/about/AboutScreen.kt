package com.zakrodionov.practicalapp.app.features.home.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zakrodionov.common.extensions.OnLaunched
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.core.navigation.LocalGlobalNavigator
import com.zakrodionov.practicalapp.app.core.ui.components.CommonCenteredButton
import com.zakrodionov.practicalapp.app.core.ui.components.CommonCenteredText
import com.zakrodionov.practicalapp.app.core.ui.components.CommonFillSpacer
import com.zakrodionov.practicalapp.app.core.ui.components.CommonSpacer
import com.zakrodionov.practicalapp.app.core.ui.defaultInsetsPadding
import com.zakrodionov.practicalapp.app.features.login.LoginFlow
import org.koin.androidx.compose.getViewModel

class AboutScreen : BaseScreen() {
    @Composable
    override fun Content() {
        super.Content()

        val globalNavigator = LocalGlobalNavigator.current
        val viewModel = getViewModel<AboutViewModel>()
        val state by viewModel.stateFlow.collectAsState()

        OnLaunched {
            viewModel.eventFlow.collect {
                when (it) {
                    NavigateToLoginFlow -> globalNavigator.push(LoginFlow(false))
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .defaultInsetsPadding()
        ) {
            val title = if (state.isLogged) stringResource(R.string.logout) else stringResource(R.string.login)
            CommonSpacer()
            CommonCenteredButton(text = title, onClick = { viewModel.loginOrLogout() })
            CommonFillSpacer()
            CommonCenteredText(stringResource(R.string.app_name) + " - " + BuildConfig.VERSION_NAME)
        }
    }
}
