package com.zakrodionov.practicalapp.app.features.home.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.zakrodionov.practicalapp.app.core.navigation.BaseScreen
import com.zakrodionov.practicalapp.app.core.ui.components.CommonCenteredButton
import com.zakrodionov.practicalapp.app.core.ui.components.CommonCenteredText
import com.zakrodionov.practicalapp.app.core.ui.components.CommonSpacer
import com.zakrodionov.practicalapp.app.core.ui.defaultInsetsPadding
import com.zakrodionov.practicalapp.app.features.home.posts.detail.ArgsPostDetail
import com.zakrodionov.practicalapp.app.features.home.posts.detail.PostDetailsScreen
import com.zakrodionov.practicalapp.app.features.login.LoginFlow

class FavoriteScreen : BaseScreen() {
    @Composable
    override fun Content() {
        super.Content()

        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .defaultInsetsPadding(),
            verticalArrangement = Arrangement.Center
        ) {
            CommonCenteredText(text = "Favorites")
            CommonSpacer()
            CommonCenteredButton(
                text = "Test Favorite Navigation - Navigate to Post Details",
                onClick = { navigator.push(PostDetailsScreen(ArgsPostDetail("60d21aeb67d0d8992e610b79"))) }
            )
            CommonSpacer()
            CommonCenteredButton(
                text = "Test Nested Login Navigation",
                onClick = { navigator.push(LoginFlow(false)) }
            )
        }
    }
}
