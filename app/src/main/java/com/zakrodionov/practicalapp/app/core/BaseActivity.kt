package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zakrodionov.common.extensions.disableFitsSystemWindows
import com.zakrodionov.practicalapp.app.core.navigation.GlobalRouter
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    open val globalRouter: GlobalRouter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.disableFitsSystemWindows()
    }
}
