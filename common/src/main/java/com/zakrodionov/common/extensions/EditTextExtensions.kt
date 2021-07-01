package com.zakrodionov.common.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.ColorRes
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.addLengthCounterColorWatcher(
    minLength: Int = 0,
    maxLength: Int = Int.MAX_VALUE,
    @ColorRes defaultColorRes: Int,
    @ColorRes errorColorRes: Int
) {
    val defaultColor = context.getCompatColorStateList(defaultColorRes)
    val errorColor = context.getCompatColorStateList(errorColorRes)

    val changeColor: (Int?) -> Unit =
        { counterTextColor = if (it in minLength..maxLength) defaultColor else errorColor }

    // Change color when we add watcher
    changeColor.invoke(editText?.text?.length)

    editText?.doAfterTextChanged {
        changeColor.invoke(it?.length)
    }
}

val EditText.isNotBlank get() = text.toString().isNotBlank()

val EditText.string get() = text.toString()

fun EditText.setTextWithSelection(text: String) {
    setText(text)
    setSelection(text.length)
}

fun EditText.setTextIfDifferent(newText: String) {
    if (string != newText) {
        setText(newText)
    }
}

// Usage:
// override fun onResume() {
//        binding.etPhone.doOnNextLayout {
//            binding.etPhone.showKeyboard()
//        }
//    }
fun EditText.showKeyboard() {
    if (requestFocus()) {
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        setSelection(text.length)
    }
}

fun EditText.hideKeyboard() {
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}
