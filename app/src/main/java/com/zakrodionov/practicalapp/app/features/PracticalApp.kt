package com.zakrodionov.practicalapp.app.features

//
// @Composable
// fun PracticalApp() {
//    PracticalAppTheme {
//        val tabs = remember { HomeTabScreen.values() }
//        val navController = rememberNavController()
//
//        Scaffold(
//            bottomBar = {
//                if (shouldShowBottomBar(navController)) {
//                    PracticalAppBottomBar(navController = navController, tabs = tabs)
//                }
//            },
//            content = { innerPaddingModifier ->
//                PracticalAppNavGraph(
//                    navController = navController,
//                    modifier = Modifier.padding(innerPaddingModifier)
//                )
//            }
//        )
//    }
// }
//
// @Composable
// fun shouldShowBottomBar(navController: NavHostController): Boolean {
//    val currentRoute = currentRoute(navController)
//    // Сюда добавляем экраны в которых надо скрывать BottomBar
//    val routesWithoutBottomBar = listOf(
//        LoginScreens.PHONE.route,
//        LoginScreens.PASSWORD.route,
//    )
//    return !routesWithoutBottomBar.contains(currentRoute)
// }
//
// @Composable
// fun currentRoute(navController: NavHostController): String? {
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    return navBackStackEntry?.destination?.route
// }
