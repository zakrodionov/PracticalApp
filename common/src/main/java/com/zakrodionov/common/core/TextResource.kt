package com.zakrodionov.common.core

import android.content.res.Resources
import android.os.Parcelable
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

sealed class TextResource : Parcelable {
    companion object {
        val empty: TextResource get() = fromText("")

        fun fromText(text: String): TextResource = SimpleTextResource(text)
        fun fromStringId(@StringRes id: Int): TextResource = IdTextResource(id)
        fun fromPlural(@PluralsRes id: Int, pluralsValue: Int): TextResource = PluralsTextResource(id, pluralsValue)
    }
}

@Parcelize
private data class SimpleTextResource(
    val text: String
) : TextResource()

@Parcelize
private data class IdTextResource(
    @StringRes val id: Int
) : TextResource()

@Parcelize
private data class PluralsTextResource(
    @PluralsRes val pluralId: Int,
    val quantity: Int
) : TextResource()

fun TextResource.asString(resources: Resources): String = when (this) {
    is SimpleTextResource -> text
    is IdTextResource -> resources.getString(id)
    is PluralsTextResource -> resources.getQuantityString(pluralId, quantity)
}
