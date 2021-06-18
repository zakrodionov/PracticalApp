package com.zakrodionov.common.extensions

import android.os.Build

// Android 8 - SDK 26.
val sdk26AndUp get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
