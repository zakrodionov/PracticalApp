package com.zakrodionov.common.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import timber.log.Timber

private const val HTTPS = "https://"
private const val HTTP = "http://"

fun Context.actionDial(phone: String) {
    if (phone.isNotEmpty()) {
        val intent = Intent(Intent.ACTION_DIAL)
            .setData(Uri.parse("tel: $phone"))

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Timber.e(e)
        }
    }
}

fun Context.actionEmail(email: String) {
    val emailWithScheme = if (email.contains("mailto:")) {
        email
    } else {
        "mailto:$email"
    }

    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse(emailWithScheme))

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
    }
}

fun Context.actionViewLink(link: String) {
    var url = link
    if (!url.startsWith(HTTP) && !url.startsWith(HTTPS)) {
        url = HTTP + url
    }
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
    }
}

fun Context.actionViewPdf(contentUri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW)
        .setDataAndType(contentUri, "application/pdf")
        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
    }
}

fun Context.actionViewImage(contentUri: Uri) {
    val intent = Intent(Intent.ACTION_VIEW)
        .setDataAndType(contentUri, "image/*")
        .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.e(e)
    }
}

fun obtainFilesResult(
    resultCode: Int,
    data: Intent?
): List<Uri> {
    if (resultCode == Activity.RESULT_OK && data != null) {
        val uris = mutableListOf<Uri>()

        // Если выбрано несколько файлов
        val clipData = data.clipData
        val itemCount = clipData?.itemCount ?: 0
        for (i in 0 until itemCount) {
            clipData?.getItemAt(i)?.let {
                uris.add(it.uri)
            }
        }

        // Если выбран один файл
        val singleUri = data.data
        singleUri?.let {
            uris.add(it)
        }

        if (isDebug) {
            Timber.d("obtainFilesResult - $uris")
        }

        return uris
    }

    return emptyList()
}

fun fileChooserIntent(
    mimeTypes: Array<String>,
    allowMultiple: Boolean
): Intent {
    val intent = Intent(Intent.ACTION_GET_CONTENT)
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, allowMultiple)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
        if (mimeTypes.isNotEmpty()) {
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
    } else {
        var mimeTypesStr = ""
        for (mimeType in mimeTypes) {
            mimeTypesStr += "$mimeType|"
        }
        intent.type = mimeTypesStr.substring(0, mimeTypesStr.length - 1)
    }

    return intent
}

// Usage
// val startForResult = registerForActivityResult(PickFiles()) {  uris ->
//
// }
// startForResult.launch(defaultMimeTypes)
class PickFiles(private val allowMultiple: Boolean = false) : ActivityResultContract<Array<String>, List<Uri>>() {

    companion object {
        val defaultMimeTypes = arrayOf("image/*", "application/pdf")
    }

    override fun createIntent(context: Context, mimeTypes: Array<String>) = fileChooserIntent(mimeTypes, allowMultiple)

    override fun parseResult(resultCode: Int, result: Intent?): List<Uri> {
        return obtainFilesResult(resultCode, result)
    }
}
