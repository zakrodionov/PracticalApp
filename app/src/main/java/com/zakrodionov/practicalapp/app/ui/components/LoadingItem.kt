package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

// В основном используется в списках, для отображения загрузки пагинации
@Composable
fun LoadingItem() {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
