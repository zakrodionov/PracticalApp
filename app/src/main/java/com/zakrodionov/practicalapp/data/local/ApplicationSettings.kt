package com.zakrodionov.practicalapp.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class ApplicationSettings(context: Context) {

    companion object {
        const val NAME_STORAGE = "Settings"

        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_IS_LOGGED = "KEY_IS_LOGGED"
    }

    private val sp: SharedPreferences by lazy { context.getSharedPreferences(NAME_STORAGE, Context.MODE_PRIVATE) }

    // Active token for request
    var token: String
        get() = sp.getString(KEY_TOKEN, "") ?: ""
        set(value) = sp.edit { putString(KEY_TOKEN, value) }

    // Active token for request
    var isLogged: Boolean
        get() = sp.getBoolean(KEY_IS_LOGGED, false)
        set(value) = sp.edit { putBoolean(KEY_IS_LOGGED, value) }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sp.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun cleanSettings() {
        sp.edit { clear() }
    }
}
