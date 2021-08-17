package com.zakrodionov.practicalapp.app.features.login.ui.phone

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.custom.PhoneKeyListener
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.common.extensions.library.setPhoneMask
import com.zakrodionov.common.extensions.setTextIfDifferent
import com.zakrodionov.common.extensions.showKeyboard
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.features.login.ui.password.PasswordScreen
import com.zakrodionov.practicalapp.app.ui.components.PhoneTextField
import com.zakrodionov.practicalapp.app.ui.components.PrimaryButton
import com.zakrodionov.practicalapp.databinding.FragmentPhoneBinding
import org.koin.androidx.compose.getStateViewModel
import org.koin.androidx.viewmodel.ext.android.stateViewModel

class PhoneFragment : BaseFragment<PhoneState, PhoneEvent>(R.layout.fragment_phone) {
    override val binding: FragmentPhoneBinding by viewBinding(FragmentPhoneBinding::bind)
    override val viewModel: PhoneViewModel by stateViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            viewModel.nextScreen()
        }
        setupEditTextPhone()
    }

    private fun setupEditTextPhone() = with(binding) {
        etPhone.keyListener = PhoneKeyListener.instance
        etPhone.setPhoneMask()
        etPhone.doAfterTextChanged {
            viewModel.setFormattedPhone(it.toString())
        }
        etPhone.requestFocus()
    }

    override fun onResume() {
        super.onResume()
        binding.etPhone.showKeyboard()
    }

    override fun render(state: PhoneState) = with(binding) {
        etPhone.setTextIfDifferent(state.formattedPhone)
        btnNext.isEnabled = state.phoneIsValid
    }

    override fun sideEffect(event: PhoneEvent) = Unit
}

object PhoneScreen : Screen {
    override val key: String = "PhoneScreen"

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
            PhoneTextField(onValueChanged = { debug(it) })
            Spacer(modifier = Modifier.height(20.dp))
            PrimaryButton(
                text = TextResource.fromStringId(R.string.next),
                onClick = { navigator?.push(PasswordScreen) }
            )
        }
    }
}
