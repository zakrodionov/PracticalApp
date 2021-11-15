package com.zakrodionov.practicalapp.app.features.about.ui.about

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.zakrodionov.practicalapp.BuildConfig
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.BaseFragment
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy
import com.zakrodionov.practicalapp.app.core.navigation.ScreenAnimationStrategy.NONE
import com.zakrodionov.practicalapp.databinding.FragmentAboutBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment<AboutState, AboutEvent>(R.layout.fragment_about) {

    override val binding: FragmentAboutBinding by viewBinding(FragmentAboutBinding::bind)
    override val viewModel: AboutViewModel by viewModel()
    override val screenAnimationStrategy: ScreenAnimationStrategy = NONE

    @SuppressLint("SetTextI18n")
    override fun setupViews(view: View, savedInstanceState: Bundle?) {
        binding.tvTitle.setOnClickListener {
            viewModel.loginOrLogout()
        }
        binding.tvAppVersion.text = getString(R.string.app_name) + " - " + BuildConfig.VERSION_NAME
    }

    override fun render(state: AboutState) {
        binding.tvTitle.text =
            if (state.isLogged) getString(R.string.logout) else getString(R.string.login)
    }

    override fun sideEffect(event: AboutEvent) = Unit
}
