package com.zakrodionov.practicalapp.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.extensions.OnLaunched

@Composable
fun PhoneTextField(
    initial: String = "",
    label: TextResource = TextResource.fromText("Enter phone"),
    onValueChanged: (String) -> Unit,
    requestFocus: Boolean = true
) {
    val focusRequester = remember { FocusRequester() }

    var phone by rememberSaveable { mutableStateOf(initial) }
    onValueChanged.invoke(phone)

    OutlinedTextField(
        value = phone,
        onValueChange = { phone = it },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .padding(horizontal = 20.dp),
        label = { Text(label.asString(LocalContext.current.resources)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
    )

    if (requestFocus) {
        OnLaunched {
            focusRequester.requestFocus()
        }
    }
}
