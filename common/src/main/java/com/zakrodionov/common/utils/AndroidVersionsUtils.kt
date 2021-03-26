package com.zakrodionov.common.utils

import android.os.Build

fun isGreaterThanL(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}

fun isGreaterThanM(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

fun isGreaterThanN(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}
