package com.zakrodionov.practicalapp.app.features.login.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Screen
import com.zakrodionov.common.extensions.hideKeyboard
import com.zakrodionov.common.extensions.setSoftInputModeAdjustPan
import com.zakrodionov.common.extensions.setSoftInputModeAlwaysVisible
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFlowFragment
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import com.zakrodionov.practicalapp.app.data.preferences.AppPreferences
import com.zakrodionov.practicalapp.app.features.bottom.BottomScreens
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.PhoneScreen
import com.zakrodionov.practicalapp.databinding.FragmentLoginFlowBinding
import dev.chrisbanes.insetter.applyInsetter
import org.koin.android.ext.android.inject

class LoginFlowFragment : BaseFlowFragment(
    R.layout.fragment_login_flow,
    R.id.fragmentContainerView,
) {

    companion object {
        private const val ARG_FROM_LAUNCH_SCREEN = "ARG_FROM_LAUNCH_SCREEN"

        fun newInstance(fromLaunchScreen: Boolean) = LoginFlowFragment().apply {
            arguments = bundleOf(ARG_FROM_LAUNCH_SCREEN to fromLaunchScreen)
        }
    }

    override val startScreen: Screen = PhoneScreen()

    private val appPreferences: AppPreferences by inject()

    private val binding: FragmentLoginFlowBinding by viewBinding(FragmentLoginFlowBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tvSkipLoginFlow.setOnClickListener { closeLoginFlow() }
            tvSkipLoginFlow.applyInsetter {
                type(statusBars = true) {
                    margin()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireActivity().window?.setSoftInputModeAlwaysVisible()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window?.setSoftInputModeAdjustPan()
    }

    fun closeLoginFlow() {
        hideKeyboard()
        appPreferences.isSkipLoginFlow = true
        if (requireArguments().getBoolean(ARG_FROM_LAUNCH_SCREEN)) {
            flowRouter.newRootFlow(BottomScreens.BottomTabsScreen())
        } else {
            flowRouter.finishFlow()
        }
    }
}
