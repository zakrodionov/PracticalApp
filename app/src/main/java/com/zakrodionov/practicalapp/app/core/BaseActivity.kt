package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.zakrodionov.common.extensions.disableFitsSystemWindows

abstract class BaseActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.disableFitsSystemWindows()
    }
}
