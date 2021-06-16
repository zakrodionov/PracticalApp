package com.zakrodionov.practicalapp.app.environment.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC

class AppPreferences(context: Context) {

    companion object {
        const val NAME_STORAGE = "Settings"

        const val KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "KEY_REFRESH_TOKEN"
        const val KEY_IS_LOGGED = "KEY_IS_LOGGED"
    }

    private val sp: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(NAME_STORAGE, masterKeyAlias, context, AES256_SIV, AES256_GCM)
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

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sp.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sp.unregisterOnSharedPreferenceChangeListener(listener)
    }

    fun clearPreferences() {
        sp.edit { clear() }
    }
}
