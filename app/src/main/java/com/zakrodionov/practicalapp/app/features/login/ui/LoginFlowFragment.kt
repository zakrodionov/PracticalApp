package com.zakrodionov.practicalapp.app.features.login.ui

import com.github.terrakok.cicerone.Screen
import com.zakrodionov.common.extensions.setSoftInputModeAdjustPan
import com.zakrodionov.common.extensions.setSoftInputModeAlwaysVisible
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFlowFragment
import com.zakrodionov.practicalapp.app.features.login.LoginScreens.PhoneScreen

class LoginFlowFragment : BaseFlowFragment(
    R.layout.layout_fragment_container,
    R.id.fragmentContainerView,
) {

    companion object {
        fun newInstance() = LoginFlowFragment()
    }

    override val startScreen: Screen = PhoneScreen()

    override fun onResume() {
        super.onResume()
        requireActivity().window?.setSoftInputModeAlwaysVisible()
    }

    override fun onStop() {
        super.onStop()
        requireActivity().window?.setSoftInputModeAdjustPan()
    }
}
