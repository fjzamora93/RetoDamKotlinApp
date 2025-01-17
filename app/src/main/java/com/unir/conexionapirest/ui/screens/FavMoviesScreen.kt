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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.ui.components.BookMarkButton
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.components.SearchField
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel

@Composable
fun FavMoviesScreen(){
    Header()
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text = "Mis películas favoritas",
            style = MaterialTheme.typography.titleLarge,
            color = MiPaletaDeColores.Gold,
            maxLines = 3,
        )



        MovieList(
            Modifier.fillMaxHeight()
        )
    }
}


@Composable
fun FavMovieList(
    modifier: Modifier = Modifier,
    movieViewModel: MovieViewModel = hiltViewModel()

) {
    movieViewModel.fetchMovies()

    val movies by movieViewModel.movies.observeAsState(emptyList())
    var filter by remember { mutableStateOf("") }

    // BARRA DE BÚSQUEDA

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie)
        }
    }
}




@Composable
fun FavMovieItem(movie: Movie) {
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
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                )
            }

            Text(
                text = movie.year ?: "Año desconocido",
                style = MaterialTheme.typography.bodyLarge
            )

            Row(){
                DetailButton(
                    onClick = {println("MOstrar detalles")}
                )

                BookMarkButton (
                    onClick = { println("Añadir a favoritos") }
                )
            }

        }

    }
    CustomHorizontalDivider()
}
