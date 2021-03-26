package com.zakrodionov.practicalapp.app.ui.login.password

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.modo.Modo
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.finishFlow
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

class PasswordViewModel(private val modo: Modo) : StubViewModel() {
    fun finish() {
        modo.finishFlow()
    }
}
