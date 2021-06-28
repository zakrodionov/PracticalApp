package com.zakrodionov.practicalapp.app.data.preferences

import android.content.Context
import com.zakrodionov.common.preferences.EncryptedPreferences

class AppPreferences(context: Context) : EncryptedPreferences(context, NAME_APP_PREFERENCES) {

    companion object {
        const val NAME_APP_PREFERENCES = "APP_PREFERENCES"

        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
        const val KEY_IS_LOGGED = "KEY_IS_LOGGED"
    }

    var accessToken: String by stringPref(KEY_ACCESS_TOKEN)

    var refreshToken: String by stringPref(KEY_REFRESH_TOKEN)

    var isLogged: Boolean by booleanPref(KEY_IS_LOGGED)
}
