package com.zakrodionov.practicalapp.app.features.home.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.navigator.LocalNavigator
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.login.phone.PhoneScreen
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredButton
import com.zakrodionov.practicalapp.app.ui.components.CommonCenteredText
import com.zakrodionov.practicalapp.app.ui.components.CommonSpacer

class FavoriteScreen : AndroidScreen() {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            CommonCenteredText(text = "Favorites")
            CommonSpacer()
            CommonCenteredButton(
                text = "Test Favorite Navigation - Navigate to Post Details",
                onClick = { navigator?.push(PostDetailsScreen(ArgsPostDetail("60d21aeb67d0d8992e610b79"))) }
            )
            CommonSpacer()
            CommonCenteredButton(
                text = "Test Login Navigation",
                onClick = { navigator?.push(PhoneScreen()) }
            )
        }
    }
}
