package com.zakrodionov.common.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC

open class EncryptedPreferences(context: Context, name: String) {

    protected val sp: SharedPreferences by lazy {
        val masterKeyAlias = MasterKeys.getOrCreate(AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(name, masterKeyAlias, context, AES256_SIV, AES256_GCM)
    }

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
