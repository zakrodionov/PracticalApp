package com.zakrodionov.practicalapp.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import com.zakrodionov.practicalapp.app.core.BaseComposeFragment
import com.zakrodionov.practicalapp.app.ui.login.password.PasswordViewModel
import org.koin.androidx.compose.getViewModel

class FavoriteFragment : BaseComposeFragment() {
    @Composable
    override fun ComposeContent() {
        val viewModel = getViewModel<FavoriteViewModel>()
        Box {

        }
    }
}

class FavoriteViewModel : StubViewModel()
