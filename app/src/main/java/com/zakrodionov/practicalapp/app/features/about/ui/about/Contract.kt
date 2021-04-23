package com.zakrodionov.practicalapp.app.features.about.ui.about

import android.os.Parcelable
import com.zakrodionov.practicalapp.app.core.BaseError
import kotlinx.parcelize.Parcelize

@Parcelize
data class AboutState(
    val isLogged: Boolean = false,
    val error: BaseError? = null,
    val isLoading: Boolean = false
) : Parcelable

sealed class AboutEvent
