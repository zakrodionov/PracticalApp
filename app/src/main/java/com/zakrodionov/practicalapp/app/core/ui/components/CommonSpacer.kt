package com.zakrodionov.practicalapp.app.core.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Можно использовать как аналог margin
@Composable
fun CommonSpacer(height: Dp = 20.dp) = Spacer(modifier = Modifier.height(height))

@Composable
fun CommonHorizontalSpacer(width: Dp = 20.dp) = Spacer(modifier = Modifier.width(width))
