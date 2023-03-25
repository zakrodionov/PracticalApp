package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zakrodionov.common.core.asString
import com.zakrodionov.common.ui.lce.ContentState
import com.zakrodionov.common.ui.lce.EmptyState
import com.zakrodionov.common.ui.lce.ErrorState
import com.zakrodionov.common.ui.lce.LceState
import com.zakrodionov.common.ui.lce.LoadingState
import com.zakrodionov.common.ui.lce.UiError
import com.zakrodionov.practicalapp.R

// Обертка лейаута для обработки состояний загрузки, контента и ошибки
@Composable
fun Lce(
    lceState: LceState,
    tryAgain: () -> Unit,
    content: @Composable () -> Unit
) {
    when (lceState) {
        ContentState -> content()
        EmptyState -> Empty()
        is ErrorState -> Error(lceState.error, tryAgain)
        is LoadingState -> Loading(lceState.isTranslucent)
    }
}

@Composable
fun Empty() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.empty_message),
            modifier = Modifier
                .clickable { }
                .padding(horizontal = 20.dp)
        )
    }
}

@Composable
fun Error(error: UiError, tryAgain: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
        Text(
            text = error.title.asString(LocalContext.current.resources),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = error.message.asString(LocalContext.current.resources),
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = tryAgain) {
                Text(text = stringResource(id = R.string.try_again), modifier = Modifier.padding(horizontal = 20.dp))
            }
        }
    }
}

@Composable
fun Loading(isTranslucent: Boolean) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().clickable { }.run {
            if (isTranslucent) background(color = MaterialTheme.colors.background) else this
        }
    ) {
        CircularProgressIndicator()
    }
}
