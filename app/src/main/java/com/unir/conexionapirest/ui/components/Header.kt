package com.unir.conexionapirest.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes

@Composable
fun Header(modifier: Modifier = Modifier) {
    val activity = LocalContext.current as Activity
    val drawerState = rememberDrawerState(DrawerValue.Closed) // Controlamos el estado del Drawer
    val scope = rememberCoroutineScope() // Usamos la coroutine para manejar el Drawer
    val navigationViewModel = LocalNavigationViewModel.current

    Row(
        modifier = modifier
            .fillMaxWidth() // Ocupa todo el ancho disponible
            .padding(8.dp), // Espaciado opcional
        horizontalArrangement = Arrangement.SpaceBetween // Espacia los elementos entre los extremos
    ) {
        // Menú de la aplicación
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "open menu",
            modifier = Modifier
                .clickable { onClickMenu(navigationViewModel) }
        )

        HomeButton()

        // Cerrar la aplicación
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "close app",
            modifier = Modifier
                .clickable { activity.finish() } // Acción al hacer clic
        )
    }

    HorizontalDivider(
        Modifier
            .background(Color(0xFFEEEEEE))
            .height(1.dp)
            .fillMaxWidth()
    )
}

// Esto debería abrir una nueva Activity que tenga el menú
fun onClickMenu(navigationViewModel: NavigationViewModel){
    navigationViewModel.navigate(ScreensRoutes.FavScreen.route)
}