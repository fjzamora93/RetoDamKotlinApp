package com.unir.conexionapirest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.unir.conexionapirest.ui.screens.LoginScreen
import com.unir.conexionapirest.ui.screens.SolicitudesScreen

import com.unir.conexionapirest.ui.screens.VacantesScreen
import com.unir.conexionapirest.ui.screens.UserProfileScreen
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel


@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer el NavigationViewModel en todo el árbol de composables dentro de NavGraph
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    val authViewModel: AuthViewModel = hiltViewModel()

    CompositionLocalProvider(
        LocalNavigationViewModel provides navigationViewModel,
        LocalAuthViewModel provides authViewModel,


        ) {
        // Declaramos el objeto que va a ser observado
        val navigationEvent by navigationViewModel.navigationEvent.observeAsState()

        // Este LaunchedEffect es un OBSERVADOR que se disparará ante cualquier evento de navegación
        LaunchedEffect(navigationEvent) {
            navigationEvent?.let { event ->
                when (event) {
                    is NavigationEvent.Navigate -> {
                        navController.navigate(event.route) {
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    is NavigationEvent.NavigateAndPopUp -> {
                        navController.navigate(event.route) {
                            popUpTo(event.popUpToRoute) {
                                inclusive = event.inclusive
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
                navigationViewModel.clearNavigationEvent()
            }
        }

        // DEFINICIÓN DE LAS RUTAS DE CADA PANTALLA
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.LoginScreen.route
        ) {

            composable(ScreensRoutes.UserScreen.route) { UserProfileScreen() }
            composable(ScreensRoutes.LoginScreen.route) { LoginScreen() }
            composable(ScreensRoutes.VacantesScreen.route) { VacantesScreen() }
            composable(ScreensRoutes.SolicitudesScreen.route) { SolicitudesScreen() }




        }
    }
}
