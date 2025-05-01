package com.unir.conexionapirest.navigation

import androidx.compose.runtime.compositionLocalOf
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel

val LocalNavigationViewModel = compositionLocalOf<NavigationViewModel> {
    error("NavigationViewModel no está disponible. Asegúrate de proporcionarlo.")
}

val LocalAuthViewModel = compositionLocalOf<AuthViewModel> {
    error("No hay usuario disponible en el contexto actual.")
}