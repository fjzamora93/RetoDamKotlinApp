package com.unir.conexionapirest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.MovieResumen
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.DislikeButton
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun FavMoviesScreen(){
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        LeftHalfDrawer(
            drawerState = drawerState,
            onClose = {
                coroutineScope.launch { drawerState.close() }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    },
                )

                Text(
                    text = "Mis películas favoritas",
                    style = MaterialTheme.typography.titleLarge,
                    color = MiPaletaDeColores.Gold,
                )

                Text(
                    text = "Va, venga, un punto extra por haber acertado con el catálogo de propuesto :)",
                    style = MaterialTheme.typography.bodySmall,
                    color = MiPaletaDeColores.IronDark,
                )

                FavMovieList()
            }
        }
    }
}


@Composable
fun FavMovieList(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel()

) {

    movieViewModel.getFavMovies()
    val movies by movieViewModel.favMovies.observeAsState(emptyList())
    println("LIstado de favoritos $movies")

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            FavMovieItem(movie)
        }
    }
}




@Composable
fun FavMovieItem(
    movie: MovieResumen,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val navigationViewModel = LocalNavigationViewModel.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
    ) {

        // Póster en la primera mitad
        AsyncImage(
            model = movie.poster, // URL de la imagen
            contentDescription = movie.title,
            modifier = Modifier
                .height(200.dp)
                .weight(1f) // Toma la mitad del ancho disponible
        )

        // Texto en la segunda mitad
        Column(
            modifier = Modifier
                .weight(1f) // Toma el doble del espacio que el póster
                .fillMaxHeight() // Asegura que ocupe toda la altura disponible
                .padding(top = 18.dp)
        ) {

            Row(){
                Text(
                    text = movie.title ?: "Título no disponible",
                    style = MaterialTheme.typography.titleSmall,
                )
            }

            Text(
                text = movie.year ?: "Año desconocido",
                style = MaterialTheme.typography.bodyLarge
            )

            Row(){
                DetailButton(
                    onClick = {
                        navigationViewModel.navigate(
                            ScreensRoutes.MovieDetailScreen.createRoute(movieID = movie.imdbID)
                        )
                    }
                )

                DislikeButton(
                    onClick = {
                        println("Eliminar de favoritos")
                        movieViewModel.removeFromFavorites(movie)
                    }
                )

            }

        }

    }
    CustomHorizontalDivider()
}
