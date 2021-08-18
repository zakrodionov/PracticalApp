package com.zakrodionov.practicalapp.app.features.root.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.zakrodionov.practicalapp.app.features.PracticalApp

class RootActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PracticalApp()
        }
    }
}
