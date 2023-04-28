package com.zakrodionov.common.extensions

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast

// Android 8 - SDK 26.
@get:ChecksSdkIntAtLeast(api = Build.VERSION_CODES.O)
val sdk26AndUp get() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
