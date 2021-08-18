package com.zakrodionov.practicalapp.app.features.login.ui.phone

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zakrodionov.common.core.TextResource
import com.zakrodionov.common.extensions.debug
import com.zakrodionov.practicalapp.R
import com.zakrodionov.practicalapp.app.features.login.LoginScreens
import com.zakrodionov.practicalapp.app.ui.components.PhoneTextField
import com.zakrodionov.practicalapp.app.ui.components.PrimaryButton

@Composable
fun PhoneScreen(navController: NavHostController) {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        PhoneTextField(onValueChanged = { debug(it) })
        Spacer(modifier = Modifier.height(20.dp))
        PrimaryButton(
            text = TextResource.fromStringId(R.string.next),
            onClick = {
                navController.navigate(LoginScreens.PASSWORD.route)
            }
        )
    }
}
