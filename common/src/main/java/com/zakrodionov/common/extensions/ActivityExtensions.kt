package com.zakrodionov.common.extensions

import android.app.Activity
import android.view.View
import android.view.ViewGroup

fun Activity.rootView() = (findViewById<View>(android.R.id.content) as? ViewGroup)
    ?.getChildAt(0) as? ViewGroup
