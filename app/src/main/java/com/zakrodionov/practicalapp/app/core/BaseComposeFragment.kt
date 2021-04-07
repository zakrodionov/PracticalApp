package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

// Temp fragment for continuous migration to compose
abstract class BaseComposeFragment : Fragment() {

    @Composable
    abstract fun ComposeContent()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        ComposeView(requireContext()).apply {
            setContent {
                ComposeContent()
            }
        }
}
