package com.zakrodionov.practicalapp.app

import android.view.View
import dev.chrisbanes.insetter.applyInsetter

fun View.applyStatusAndNavigationBarsInsetsPadding() {
    applyInsetter {
        type(statusBars = true, navigationBars = true) {
            padding()
        }
    }
}
