package com.unir.conexionapirest.navigation

import androidx.compose.runtime.compositionLocalOf

val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}