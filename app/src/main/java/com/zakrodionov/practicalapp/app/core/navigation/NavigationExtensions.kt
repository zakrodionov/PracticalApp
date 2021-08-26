package com.zakrodionov.practicalapp.app.core.navigation

import cafe.adriel.voyager.navigator.Navigator

// Удобно использовать во вложенной навигации(или Flow) для закрытия всей вложенной навигации
fun Navigator.popRoot() = parent?.pop() ?: popUntilRoot()