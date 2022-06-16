package com.zakrodionov.practicalapp.app.features.login.ui.password

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.showKeyboard
import com.zakrodionov.common.extensions.textString
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.StubFragment
import com.zakrodionov.practicalapp.app.features.StubViewModel
import com.zakrodionov.practicalapp.app.features.login.ui.LoginFlowFragment
import com.zakrodionov.practicalapp.databinding.FragmentPasswordBinding
import dev.chrisbanes.insetter.applyInsetter
import org.koin.android.ext.android.get
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
            (parentFragment as? LoginFlowFragment)?.closeLoginFlow()
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

    override fun applyInsets() = Unit
}

class PasswordViewModel(private val flowRouter: FlowRouter) : StubViewModel() {
    fun finishFlow() {
        flowRouter.finishFlow()
    }
}
