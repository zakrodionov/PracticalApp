package com.zakrodionov.common.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

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

    //region prefs ReadWriteProperty
    fun stringPref(key: String, defValue: String = "") =
        object : BaseEncryptedPreferencesReadWriteProperty<String>() {
            override fun getValue(sp: SharedPreferences): String {
                return sp.getString(key, defValue) ?: defValue
            }

            override fun setValue(sp: SharedPreferences, value: String) {
                sp.edit { putString(key, value) }
            }
        }

    fun booleanPref(key: String, defValue: Boolean = false) =
        object : BaseEncryptedPreferencesReadWriteProperty<Boolean>() {
            override fun getValue(sp: SharedPreferences): Boolean {
                return sp.getBoolean(key, defValue)
            }

            override fun setValue(sp: SharedPreferences, value: Boolean) {
                sp.edit { putBoolean(key, value) }
            }
        }

    fun intPref(key: String, defValue: Int = 0) =
        object : BaseEncryptedPreferencesReadWriteProperty<Int>() {
            override fun getValue(sp: SharedPreferences): Int {
                return sp.getInt(key, defValue)
            }

            override fun setValue(sp: SharedPreferences, value: Int) {
                sp.edit { putInt(key, value) }
            }
        }

    fun longPref(key: String, defValue: Long = 0) =
        object : BaseEncryptedPreferencesReadWriteProperty<Long>() {
            override fun getValue(sp: SharedPreferences): Long {
                return sp.getLong(key, defValue)
            }

            override fun setValue(sp: SharedPreferences, value: Long) {
                sp.edit { putLong(key, value) }
            }
        }

    fun floatPref(key: String, defValue: Float = 0f) =
        object : BaseEncryptedPreferencesReadWriteProperty<Float>() {
            override fun getValue(sp: SharedPreferences): Float {
                return sp.getFloat(key, defValue)
            }

            override fun setValue(sp: SharedPreferences, value: Float) {
                sp.edit { putFloat(key, value) }
            }
        }

    fun stringSetPref(key: String, defValue: Set<String> = setOf()) =
        object : BaseEncryptedPreferencesReadWriteProperty<Set<String>>() {
            override fun getValue(sp: SharedPreferences): Set<String> {
                return sp.getStringSet(key, defValue) ?: defValue
            }

            override fun setValue(sp: SharedPreferences, value: Set<String>) {
                sp.edit { putStringSet(key, value) }
            }
        }

    abstract class BaseEncryptedPreferencesReadWriteProperty<T : Any> :
        ReadWriteProperty<EncryptedPreferences, T> {

        override fun getValue(thisRef: EncryptedPreferences, property: KProperty<*>): T {
            return getValue(thisRef.sp)
        }

        override fun setValue(thisRef: EncryptedPreferences, property: KProperty<*>, value: T) {
            setValue(thisRef.sp, value)
        }

        abstract fun getValue(sp: SharedPreferences): T
        abstract fun setValue(sp: SharedPreferences, value: T)
    }
    //endregion
}
