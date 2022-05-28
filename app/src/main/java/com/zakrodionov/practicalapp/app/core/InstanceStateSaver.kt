package com.zakrodionov.practicalapp.app.core

import android.os.Bundle

interface InstanceStateSaver {
    fun onRestoreInstanceState(savedInstanceState: Bundle?)
    fun onSaveInstanceState(outState: Bundle)
}
