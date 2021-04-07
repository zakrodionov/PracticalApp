package com.zakrodionov.practicalapp.app.ui.login.password

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.terrakok.modo.Modo
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import com.zakrodionov.practicalapp.app.core.navigation.finishFlow
import com.zakrodionov.practicalapp.app.ui.StubViewModel
import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import org.koin.android.ext.android.get
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO for testing flow
class PasswordFragment : BaseComposeFragment() {
    @Composable
    override fun ComposeContent() {
        PasswordScreen()
    }
}

@Composable
@Preview
fun PasswordScreen() {
    val viewModel = getViewModel<PasswordViewModel>()

    var password by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        Button(
            onClick = { viewModel.finish() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Next")
        }
    }
}

class PasswordViewModel(
    private val modo: Modo,
    private val applicationSettings: ApplicationSettings
) : StubViewModel() {
    fun finish() {
        applicationSettings.isLogged = true
        modo.finishFlow()
    }
}
