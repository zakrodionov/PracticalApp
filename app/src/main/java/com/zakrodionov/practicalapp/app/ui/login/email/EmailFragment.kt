package com.zakrodionov.practicalapp.app.ui.login.email

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.FlowModoRouter
import com.zakrodionov.practicalapp.app.ui.Screens
import com.zakrodionov.practicalapp.app.ui.StubFragment
import com.zakrodionov.practicalapp.app.ui.StubViewModel
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

class EmailViewModel(private val flowModoRouter: FlowModoRouter) : StubViewModel() {
    fun nextScreen() {
        flowModoRouter.forward(Screens.PasswordScreen())
    }
}
