package com.zakrodionov.practicalapp.app.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

// Занимает все свободное пространство (вес = 1)
@Composable
fun ColumnScope.CommonFillSpacer() = Spacer(modifier = Modifier.weight(1f))
