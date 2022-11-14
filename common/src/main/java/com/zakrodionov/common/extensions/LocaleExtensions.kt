package com.zakrodionov.common.extensions

import android.content.res.Resources
import androidx.core.os.ConfigurationCompat
import java.time.ZoneId
import java.util.Locale

// Default Locale of your application
val currentLocale: Locale get() = Locale.getDefault()

// Default Locale of your phone
val currentDeviceLocale: Locale? get() = ConfigurationCompat.getLocales(Resources.getSystem().configuration)[0]

val currentZoneId: ZoneId get() = ZoneId.systemDefault()
