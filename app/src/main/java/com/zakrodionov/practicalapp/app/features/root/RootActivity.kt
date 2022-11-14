package com.zakrodionov.practicalapp.app.features.root

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.zakrodionov.common.extensions.disableFitsSystemWindows
import com.zakrodionov.practicalapp.app.features.notifications.FirebaseMsgService.Companion.ARG_PUSH_DATA
import com.zakrodionov.practicalapp.app.ui.theme.PracticalAppTheme

class RootActivity : ComponentActivity() {

    private val deepLinkScreens = mutableStateOf<List<String>>(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
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
        val screen = intent?.extras?.getString(ARG_PUSH_DATA)

        if (savedInstanceState == null && screen != null) {
            deepLinkScreens.value = mutableListOf(screen)
        }
    }

    @Composable
    fun RootScreen() {
        val deepLinkScreens by rememberSaveable { deepLinkScreens }

        DeepLinkHandler.handleDeepLink(deepLinkScreens = deepLinkScreens)
    }
}
