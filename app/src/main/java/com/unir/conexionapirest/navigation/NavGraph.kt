package com.unir.conexionapirest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.unir.conexionapirest.ui.screens.FavMoviesScreen
import com.unir.conexionapirest.ui.screens.MainScreen
import com.unir.conexionapirest.ui.screens.MovieDetailScreen


@Composable
fun NavGraph(
    navController: NavHostController
) {
    // Proveer el NavigationViewModel en todo el árbol de composables dentro de NavGraph
    val navigationViewModel: NavigationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

    CompositionLocalProvider(LocalNavigationViewModel provides navigationViewModel) {
        // Declaramos el objeto que va a ser observado
        val navigationEvent by navigationViewModel.navigationEvent.observeAsState()

        // Este LaunchedEffect es un OBSERVADOR que se disparará ante cualquier evento de navegación
        LaunchedEffect(navigationEvent) {
            //println("Navegando a la ruta: $navigationEvent")
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

        // Aquí va el NavHost, donde defines las rutas de las pantallas
        NavHost(
            navController = navController,
            startDestination = ScreensRoutes.MainScreen.route
        ) {

            // Pantalla de inicio
            composable(ScreensRoutes.MainScreen.route) {
                MainScreen()
            }

            // Pantalla de Favoritos
            composable(ScreensRoutes.FavScreen.route) {
                FavMoviesScreen()
            }


            // Pantalla de detalles de películas
            composable(
                ScreensRoutes.MovieDetailScreen.route,
                arguments = listOf(navArgument("movieID") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieID") ?: "0"
                MovieDetailScreen(movieId = movieId)
            }
        }
    }
}
