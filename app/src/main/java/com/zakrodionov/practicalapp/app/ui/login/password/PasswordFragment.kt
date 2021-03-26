package com.zakrodionov.practicalapp.app.ui.login.password

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowModoRouter
import com.zakrodionov.practicalapp.app.ui.StubFragment
import com.zakrodionov.practicalapp.app.ui.StubViewModel
import com.zakrodionov.practicalapp.data.local.ApplicationSettings
import com.zakrodionov.practicalapp.databinding.FragmentPasswordBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
// TODO for testing flow
class PasswordFragment : StubFragment(R.layout.fragment_password) {
    override val binding: FragmentPasswordBinding by viewBinding(FragmentPasswordBinding::bind)
    override val viewModel: PasswordViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            get<ApplicationSettings>().isLogged = true
            viewModel.finish()
        }
    }
}

class PasswordViewModel(private val flowModoRouter: FlowModoRouter) : StubViewModel() {
    fun finish() {
        flowModoRouter.finishFlow()
    }
}
