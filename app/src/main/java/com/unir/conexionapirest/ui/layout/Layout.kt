package com.unir.conexionapirest.ui.layout


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkAdded
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.theme.CustomColors

import kotlinx.coroutines.launch

/**
 * Composable plantilla, disponible para todas las screens, que incluye:
 * - Header.
 * - Menú lateral desplegable.
 * - Footer.
 * */
@Composable
fun MainLayout(
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
){

    val nav: NavigationViewModel = LocalNavigationViewModel.current

    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf("Perfil", "Vacantes", "Solicitudes")

    Scaffold(
        floatingActionButton = floatingActionButton,
        bottomBar = {
            NavigationBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White,
                contentColor = Color.Black,
                tonalElevation = 4.dp
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            when (item) {
                                "Perfil" -> {
                                    // Navegar a la pantalla de perfil cuando se hace clic en el ícono de "Profile"
                                    nav.navigate(ScreensRoutes.UserScreen.route)
                                }
                                "Solicitudes" -> {
                                    // Aquí también puedes definir una navegación para la pantalla Home si es necesario
                                    nav.navigate(ScreensRoutes.SolicitudesScreen.route)
                                }
                                "Vacantes" -> {
                                    // Y lo mismo para la pantalla de búsqueda
                                    nav.navigate(ScreensRoutes.VacantesScreen.route)
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = when (item) {
                                    "Perfil" -> Icons.Default.Person
                                    "Vacantes" -> Icons.Default.Search
                                    "Solicitudes" -> Icons.Default.BookmarkAdded
                                    else -> Icons.Default.Home
                                },
                                contentDescription = item
                            )
                        },
                        label = { Text(item) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(CustomColors.BlackGradient)
                .padding(innerPadding)
        ) {

            // CONTENIDO DEL SCREEN. MODIFICAR SI FUESE NECESARIO.
            LazyColumn(Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp , horizontal = 0.dp)
            ){
                item {
                    content()
                }
            }
        }
    }



}


