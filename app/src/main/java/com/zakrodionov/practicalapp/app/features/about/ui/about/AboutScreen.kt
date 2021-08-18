package com.zakrodionov.practicalapp.app.features.about.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.R
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.getStateViewModel

@Composable
fun AboutScreen(navigateToLogin: () -> Unit) {
    val viewModel = getStateViewModel<AboutViewModel>()
    val state = viewModel.stateFlow.collectAsState()

    LaunchedEffect(true) {
        viewModel.eventFlow.collect {
            when (it) {
                NavigateToLoginFlow -> navigateToLogin()
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        val title = if (state.value.isLogged) stringResource(R.string.logout) else stringResource(R.string.login)
        Text(
            text = title,
            modifier = Modifier
                .clickable { viewModel.loginOrLogout() }
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.app_name) + " - " + BuildConfig.VERSION_NAME,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            textAlign = TextAlign.Center
        )
    }
}
