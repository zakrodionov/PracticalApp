package com.zakrodionov.practicalapp.app.features.login.ui.email

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowRouter
import com.zakrodionov.practicalapp.app.features.StubFragment
import com.zakrodionov.practicalapp.app.features.StubViewModel
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.PasswordScreen
import com.zakrodionov.practicalapp.databinding.FragmentEmailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

// TODO for testing flow
class EmailFragment : StubFragment(R.layout.fragment_email) {
    override val binding: FragmentEmailBinding by viewBinding(FragmentEmailBinding::bind)
    override val viewModel: EmailViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            viewModel.nextScreen()
        }
    }
}

class EmailViewModel(private val flowRouter: FlowRouter) : StubViewModel() {
    fun nextScreen() {
        flowRouter.navigateTo(PasswordScreen())
    }
}
