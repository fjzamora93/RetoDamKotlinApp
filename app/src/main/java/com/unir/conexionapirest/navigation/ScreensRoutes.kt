package com.unir.conexionapirest.navigation

sealed class ScreensRoutes(val route: String) {

    object MainScreen : ScreensRoutes("MainScreen")

    object LoginScreen : ScreensRoutes("LoginScreen")
    object UserScreen : ScreensRoutes("UserScreen")
    object VacantesScreen : ScreensRoutes("VacantesScreen")
    object SolicitudesScreen : ScreensRoutes("SolicitudesScreen")


}