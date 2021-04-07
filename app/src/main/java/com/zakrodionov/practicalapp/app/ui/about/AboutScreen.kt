package com.zakrodionov.practicalapp.app.ui.about

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.compose.getViewModel

class AboutFragment : BaseComposeFragment() {

    @Composable
    override fun ComposeContent() {
        AboutScreen()
    }
}

@Composable
@Preview
fun AboutScreen() {
    val viewModel = getViewModel<AboutViewModel>()

    val state = viewModel.stateFlow.collectAsState()

    Box {
        Text(
            text = if (state.value.isLogged) "Logout" else "Login",
            modifier = Modifier
                .padding(20.dp)
                .clickable { viewModel.loginOrLogout() }
        )
    }
}
