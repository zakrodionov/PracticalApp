package com.zakrodionov.practicalapp.app.ui.login.email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.forward
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.ui.StubFragment
import com.zakrodionov.practicalapp.app.ui.StubViewModel
import com.zakrodionov.practicalapp.app.ui.login.PasswordScreen
import com.zakrodionov.practicalapp.databinding.FragmentEmailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO for testing flow
class EmailFragment : StubFragment(R.layout.fragment_email) {
    override val binding: FragmentEmailBinding by viewBinding(FragmentEmailBinding::bind)
    override val viewModel: EmailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
            setContent {
                EmailView(onClick = { viewModel.nextScreen() })
            }
        }
}

@Composable
@Preview
fun EmailView(onClick: (email: String) -> Unit = {}) {
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
            modifier = Modifier.fillMaxWidth().padding(20.dp)
        )
        Button(onClick = { onClick.invoke(email) }, modifier = Modifier.fillMaxWidth().padding(20.dp)) {
            Text(text = "Next")
        }
    }
}

class EmailViewModel(private val modo: Modo) : StubViewModel() {
    fun nextScreen() {
        modo.forward(PasswordScreen())
    }
}
