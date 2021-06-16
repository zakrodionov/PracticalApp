package com.zakrodionov.practicalapp.app.data.preferences

import android.content.Context
import androidx.core.content.edit
import com.zakrodionov.common.preferences.EncryptedPreferences

class AppPreferences(context: Context) : EncryptedPreferences(context, NAME_APP_PREFERENCES) {

    companion object {
        const val NAME_APP_PREFERENCES = "APP_PREFERENCES"

        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
        const val KEY_IS_LOGGED = "KEY_IS_LOGGED"
    }

    var accessToken: String
        get() = sp.getString(KEY_ACCESS_TOKEN, "") ?: ""
        set(value) = sp.edit { putString(KEY_ACCESS_TOKEN, value) }

    var refreshToken: String
        get() = sp.getString(KEY_REFRESH_TOKEN, "") ?: ""
        set(value) = sp.edit { putString(KEY_REFRESH_TOKEN, value) }

    var isLogged: Boolean
        get() = sp.getBoolean(KEY_IS_LOGGED, false)
        set(value) = sp.edit { putBoolean(KEY_IS_LOGGED, value) }
}
