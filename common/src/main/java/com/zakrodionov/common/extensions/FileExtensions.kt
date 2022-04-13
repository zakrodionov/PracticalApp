package com.zakrodionov.common.extensions

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import androidx.core.content.FileProvider
import java.io.File

fun decodeBase64(base: String): ByteArray = Base64.decode(base, 0)

val Context.authority: String get() = applicationContext.packageName + ".provider"

fun File.contentUri(context: Context): Uri =
    FileProvider.getUriForFile(context, context.authority, this)

fun Context.getFileName(uri: Uri): String? = when (uri.scheme) {
    ContentResolver.SCHEME_FILE -> uri.lastPathSegment
    ContentResolver.SCHEME_CONTENT -> getCursorContent(uri)
    else -> null
}

@SuppressLint("Range")
private fun Context.getCursorContent(uri: Uri): String? {
    return contentResolver.query(uri, null, null, null, null)?.use { cursor ->
        if (cursor.moveToFirst()) cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)) else null
    }
}
