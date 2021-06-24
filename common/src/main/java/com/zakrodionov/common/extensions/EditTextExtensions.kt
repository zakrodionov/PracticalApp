package com.zakrodionov.common.extensions

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

fun EditText.setTextWithSelection(text: String) {
    setText(text)
    setSelection(text.length)
}
