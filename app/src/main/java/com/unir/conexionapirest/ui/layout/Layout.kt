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
import androidx.compose.runtime.collectAsState
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
import com.unir.conexionapirest.navigation.LocalAuthViewModel
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.theme.CustomColors
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel
import com.unir.conexionapirest.ui.viewmodels.UserState

import kotlinx.coroutines.launch

/**
 * Composable plantilla, disponible para todas las screens, que incluye:
 * - Header.
 * - Menú lateral desplegable.
 * - Footer.
 * */
@Composable
fun MainLayout(
    authViewModel: AuthViewModel = LocalAuthViewModel.current,

    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
    ){

    val nav: NavigationViewModel = LocalNavigationViewModel.current

    val userState by authViewModel.userState.collectAsState()


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
                if (userState is UserState.Success) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selectedItem == index,
                            onClick = {

                                selectedItem = index
                                when (item) {
                                    "Perfil" -> {
                                        nav.navigate(ScreensRoutes.UserScreen.route)
                                    }

                                    "Solicitudes" -> {
                                        nav.navigate(ScreensRoutes.SolicitudesScreen.route)
                                    }

                                    "Vacantes" -> {
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


