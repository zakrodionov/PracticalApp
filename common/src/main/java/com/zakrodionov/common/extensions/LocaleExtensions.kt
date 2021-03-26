package com.zakrodionov.common.extensions

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.time.ZoneId
import java.util.Locale

val currentLocale: Locale
    get() = ConfigurationCompat.getLocales(Resources.getSystem().configuration)
        .takeIf { !it.isEmpty }?.get(0)
        ?: Locale.getDefault()

val currentZoneId: ZoneId = ZoneId.systemDefault()
