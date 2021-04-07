package com.zakrodionov.practicalapp.app.ui.login.email

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
import com.github.terrakok.modo.forward
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import com.zakrodionov.practicalapp.app.ui.StubViewModel
import com.zakrodionov.practicalapp.app.ui.login.PasswordScreen
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailFragment : BaseComposeFragment() {
    @Composable
    override fun ComposeContent() {
        EmailScreen()
    }
}

@Composable
@Preview
fun EmailScreen() {
    val viewModel = getViewModel<EmailViewModel>()

    var email by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )
        Button(
            onClick = { viewModel.nextScreen() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Text(text = "Next")
        }
    }
}

class EmailViewModel(private val modo: Modo) : StubViewModel() {
    fun nextScreen() {
        modo.forward(PasswordScreen())
    }
}
