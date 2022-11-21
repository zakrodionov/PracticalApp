package com.zakrodionov.practicalapp.app.features.root

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.zakrodionov.common.extensions.disableFitsSystemWindows
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.notifications.FirebaseMsgService.Companion.ARG_PUSH_DATA
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

class RootActivity : ComponentActivity() {

    private val deepLinkNavigation: MutableState<DeepLinkNavigation> = mutableStateOf(DeepLinkNavigation.empty)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_PracticalApp_Main)

        super.onCreate(savedInstanceState)

        window?.disableFitsSystemWindows()

        setContent {
            PracticalAppTheme {
                RootScreen()
            }
        }

        handleIntent(intent, savedInstanceState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        handleIntent(intent, null)
    }

    private fun handleIntent(intent: Intent?, savedInstanceState: Bundle?) {
        val deepLinkNavigation = intent?.extras?.getParcelable(ARG_PUSH_DATA) as? DeepLinkNavigation

        if (savedInstanceState == null && deepLinkNavigation != null) {
            this.deepLinkNavigation.value = deepLinkNavigation
        }
    }

    @Composable
    fun RootScreen() {
        DeepLinkHandler.HandleDeepLink(deepLinkNavigation = deepLinkNavigation.value)
    }
}
