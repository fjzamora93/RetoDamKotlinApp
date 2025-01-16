package com.unir.conexionapirest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.ui.components.BackButton
import com.unir.conexionapirest.ui.components.BookMarkButton
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: String,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    // Observe the movie data from the ViewModel
    // Trigger movie fetch when movieId changes
    movieViewModel.fetchMovieById(movieId)
    LaunchedEffect(movieId) {
        movieViewModel.fetchMovieById(movieId)
    }
    val movie by movieViewModel.selectedMovie.observeAsState()


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
    ) {
        Column(){
            Header()
            Text("HOLA")


        println("Y esta es la películae n el screen de detalles: $movie")
        // If movie is null, show loading or empty state
        if (movie != null) {
            // No need to remember the state, just use the observed movie directly
            Text("Detalles de la película")

            AsyncImage(
                model = movie!!.poster,  // Access directly
                contentDescription = movie?.title ?: "Título no disponible",
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f)
            )

            Text(movie?.title ?: "No Title")

            // Add more details here as needed
        } else {
            // Show a loading indicator or placeholder
            Column(){
                Text("Cargando detalles...")

                BookMarkButton(
                    onClick = { println("Añadir a favoritos")
                    }
                )
            }
        }
        }
    }
}
