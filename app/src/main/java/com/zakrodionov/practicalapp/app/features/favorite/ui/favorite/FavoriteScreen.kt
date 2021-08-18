package com.zakrodionov.practicalapp.app.features.favorite.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FavoriteScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Favorites")
    }
}
