package com.zakrodionov.practicalapp.app.features.home.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredButton
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredText
import com.zakrodionov.practicalapp.app.ui.components.CommonSpacer

@Composable
fun FavoriteScreen(navigateToPostDetail: (postId: String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        CommonCenteredText(text = "Favorites")
        CommonSpacer()
        CommonCenteredButton(
            text = "Test Favorite Navigation - Navigate to Post Details",
            onClick = { navigateToPostDetail("60d21aeb67d0d8992e610b79") }
        )
    }
}
