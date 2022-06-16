package com.zakrodionov.practicalapp.app.core.navigation

import com.github.terrakok.cicerone.BackTo
import com.github.terrakok.cicerone.Forward
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen

// Для навигация во всем приложение, обычно полноэкранные фичи. Создается в 1 экземпляре.
class GlobalRouter : Router()
