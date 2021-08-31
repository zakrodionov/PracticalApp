package com.zakrodionov.practicalapp.app.core

import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import java.util.UUID
import kotlin.properties.Delegates

const val KEY_UNIQUE_ID = "KEY_UNIQUE_ID"

// A usual fragment that has a unique id.
// The id is created on the first call onCreate()
// Unique ID is useful when using scopes in Di for specific fragments
abstract class BaseUniqueIdFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected var uniqueId: String by Delegates.notNull()
        private set

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uniqueId = savedInstanceState?.getString(KEY_UNIQUE_ID) ?: createUniqueId()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY_UNIQUE_ID, uniqueId)
    }

    private fun createUniqueId(): String = this::class.qualifiedName + UUID.randomUUID().toString()
}
