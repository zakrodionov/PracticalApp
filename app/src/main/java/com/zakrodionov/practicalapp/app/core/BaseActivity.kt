package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zakrodionov.common.extensions.disableFitsSystemWindows

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.disableFitsSystemWindows()
    }
}
