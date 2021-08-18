package com.zakrodionov.practicalapp.app.features.login.ui.password

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.showKeyboard
import com.zakrodionov.common.extensions.textString
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.StubFragment
import com.zakrodionov.practicalapp.app.features.StubViewModel
import com.zakrodionov.practicalapp.app.ui.components.PasswordTextField
import com.zakrodionov.practicalapp.app.ui.components.PrimaryButton
import com.zakrodionov.practicalapp.databinding.FragmentPasswordBinding
import org.koin.android.ext.android.get
import org.koin.androidx.compose.get
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO for testing flow
class PasswordFragment : StubFragment(R.layout.fragment_password) {
    override val binding: FragmentPasswordBinding by viewBinding(FragmentPasswordBinding::bind)
    override val viewModel: PasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            binding.etPassword.hideKeyboard()
            get<AppPreferences>().isLogged = true
            viewModel.finish()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.etPassword.showKeyboard()
    }

    // Test custom onBackPressed
    override fun onBackPressed(): Boolean {
        return binding.etPassword.textString != "42"
    }
}

class PasswordViewModel(private val flowRouter: FlowRouter) : StubViewModel() {
    fun finish() {
        flowRouter.finishFlow()
    }
}

@Composable
fun PasswordScreen(popToRoot: () -> Unit) {
    val appPreferences = get<AppPreferences>()

    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        PasswordTextField(onValueChanged = { debug(it) })
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryButton(
            text = TextResource.fromStringId(R.string.next),
            onClick = {
                appPreferences.isLogged = true
                popToRoot()
            }
        )
    }
}
