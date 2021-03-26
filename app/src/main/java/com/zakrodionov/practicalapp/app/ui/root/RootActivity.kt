package com.zakrodionov.practicalapp.app.ui.root

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.modo.Modo
import com.github.terrakok.modo.android.init
import com.github.terrakok.modo.android.saveState
import com.github.terrakok.modo.back
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.core.navigation.ModoRenderWithAnimation
import com.zakrodionov.practicalapp.app.ui.Screens
import org.koin.android.ext.android.inject

class RootActivity : AppCompatActivity() {

    private val modo: Modo by inject()

    private val modoRender by lazy {
        object : ModoRenderWithAnimation(this@RootActivity, R.id.clRoot) {
            override fun createMultiStackFragment() = RootMultiStackFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        modo.init(savedInstanceState, Screens.multiStack())
        registerOnBackPressedDispatcher()
    }

    override fun onResume() {
        super.onResume()
        modo.render = modoRender
    }

    override fun onPause() {
        modo.render = null
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        modo.saveState(outState)
    }

    private fun registerOnBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    modo.back()
                }
            }
        )
    }
}
