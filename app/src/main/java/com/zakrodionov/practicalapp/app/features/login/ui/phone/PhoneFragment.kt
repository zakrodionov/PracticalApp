package com.zakrodionov.practicalapp.app.features.login.ui.phone

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.custom.PhoneKeyListener
import com.zakrodionov.common.extensions.library.setPhoneMask
import com.zakrodionov.common.extensions.setTextIfDifferent
import com.zakrodionov.common.extensions.showKeyboard
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.databinding.FragmentPhoneBinding
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
        etPhone.showKeyboard()
    }

    override fun render(state: PhoneState) = with(binding) {
        etPhone.setTextIfDifferent(state.formattedPhone)
        btnNext.isEnabled = state.phoneIsValid
    }

    override fun sideEffect(event: PhoneEvent) = Unit
}
