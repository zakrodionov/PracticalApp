@file:Suppress("TooManyFunctions")

package com.zakrodionov.common.extensions

import android.app.Activity
import android.content.Context
import android.os.Parcelable
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StyleRes
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.zakrodionov.common.R
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.dialogs.CommonDialog

val Fragment.appContext: Context get() = requireActivity().applicationContext

fun Fragment.getCurrentFragment(): Fragment? =
    childFragmentManager.fragments.firstOrNull { !it.isHidden }

fun Fragment.showToast(text: String?, length: Int = Toast.LENGTH_SHORT) {
    requireContext().toast(text, length)
}

fun Fragment.showKeyboard() {
    (requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?)?.apply {
        toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    }
}

fun Fragment.hideKeyboard() {
    (requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?)?.apply {
        hideSoftInputFromWindow(view?.windowToken, 0)
    }
}

inline fun Fragment.askForMultiplePermissions(
    crossinline onDenied: () -> Unit = {},
    crossinline onPermissionsGranted: () -> Unit = {}
) = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
    val granted = result.map { it.value }.filter { it == false }
    if (granted.isEmpty()) onPermissionsGranted() else onDenied()
}

inline fun Fragment.askForSinglePermission(
    crossinline onDenied: () -> Unit = {},
    crossinline onPermissionsGranted: () -> Unit = {}
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) onPermissionsGranted() else onDenied()
}

/*region fragment with parcelable initial arguments*/
private const val BUNDLE_INITIAL_ARGS = "BUNDLE_INITIAL_ARGS"

/**
 * Pass parcelable arguments to [F] fragment with [BUNDLE_INITIAL_ARGS] key and return [F] fragment.
 *
 * @receiver target [Fragment].
 */
fun <F : Fragment> F.withInitialArguments(params: Parcelable) = apply {
    arguments = bundleOf(BUNDLE_INITIAL_ARGS to params)
}

/**
 * Return parcelable argument which was be passed by [withInitialArguments] method.
 *
 * @receiver target [Fragment].
 * @param [T] Argument type.
 * @throws IllegalArgumentException when parcelable argument with key [BUNDLE_INITIAL_ARGS] not found.
 * @return Argument as [T].
 */
fun <T : Parcelable> Fragment.initialArguments(): T {
    return requireArguments().getParcelable<T>(BUNDLE_INITIAL_ARGS)
        ?: throw IllegalArgumentException("Fragment doesn't contain initial args")
}
/*endregion*/

fun DialogFragment.showIfNotAlreadyShown(fragmentManager: FragmentManager, tag: String, now: Boolean = true) {
    if (fragmentManager.findFragmentByTag(tag) == null) {
        if (now) showNow(fragmentManager, tag) else show(fragmentManager, tag)
    }
}

fun DialogFragment.showWithPreventMultiple(fragmentManager: FragmentManager, tag: String, now: Boolean = true) {
    (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismiss()

    if (now) showNow(fragmentManager, tag) else show(fragmentManager, tag)
}

@Suppress("LongParameterList")
fun showDialog(
    fragmentManager: FragmentManager,
    tag: String,
    title: TextResource? = null,
    message: TextResource? = null,
    btnPositiveText: TextResource? = null,
    btnNegativeText: TextResource? = null,
    showBtnNegative: Boolean = false,
    cancelable: Boolean = false,
    payload: Parcelable? = null,
    @StyleRes theme: Int? = R.style.AlertDialog_Theme,
    @StyleRes messageTextAppearance: Int? = null
) {
    CommonDialog.Builder()
        .title(title)
        .message(message)
        .btnPositive(btnPositiveText)
        .btnNegative(btnNegativeText)
        .negativeVisible(showBtnNegative)
        .cancelable(cancelable)
        .payload(payload)
        .theme(theme)
        .messageTextAppearance(messageTextAppearance)
        .tag(tag)
        .build()
        .showWithPreventMultiple(fragmentManager, tag)
}

fun FragmentTransaction.setDefaultAnimations() =
    setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_right)
