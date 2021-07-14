package com.zakrodionov.common.extensions

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.hasPermissions(vararg permissions: String): Boolean =
    requireContext().hasPermissions(*permissions)

fun Context.hasPermissions(vararg permissions: String): Boolean = permissions.all { permission ->
    ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.requestPermission(
    vararg permissions: String,
    onGranted: () -> Unit,
    onShowRationale: () -> Unit,
    onRequestPermission: () -> Unit,
) {
    when {
        hasPermissions(*permissions) -> onGranted.invoke()
        shouldShowRequestPermissionRationale(*permissions) -> onShowRationale.invoke()
        else -> onRequestPermission.invoke()
    }
}

fun Fragment.requestPermissionWithoutRationale(
    vararg permissions: String,
    onGranted: () -> Unit,
    onRequestPermission: () -> Unit,
) {
    when {
        hasPermissions(*permissions) -> onGranted.invoke()
        else -> onRequestPermission.invoke()
    }
}

fun Fragment.askMultiplePermissions(
    vararg permissions: String,
    onGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onNeverAskAgain: () -> Unit,
) = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
    val isGranted = result.all { it.value == true }
    when {
        isGranted -> onGranted.invoke()
        !isGranted && !shouldShowRequestPermissionRationale(*permissions) -> onNeverAskAgain.invoke()
        else -> onPermissionDenied.invoke()
    }
}

fun Fragment.askSinglePermission(
    permission: String,
    onGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onNeverAskAgain: () -> Unit,
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
    when {
        isGranted -> onGranted.invoke()
        !isGranted && !shouldShowRequestPermissionRationale(permission) -> onNeverAskAgain.invoke()
        else -> onPermissionDenied.invoke()
    }
}

fun Fragment.shouldShowRequestPermissionRationale(vararg permissions: String): Boolean {
    for (permission in permissions) {
        if (shouldShowRequestPermissionRationale(permission)) {
            return true
        }
    }
    return false
}
