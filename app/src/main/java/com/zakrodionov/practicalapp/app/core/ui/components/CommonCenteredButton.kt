package com.zakrodionov.practicalapp.app.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CommonCenteredButton(text: String, onClick: () -> Unit) =
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        CommonCenteredText(text = text)
    }

@Preview
@Composable
fun PreviewCommonCenteredButton() {
    CommonCenteredButton("Done") {}
}
