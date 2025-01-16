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
    val movie by movieViewModel.selectedMovie.observeAsState()

    LaunchedEffect(movieId) {
        movieViewModel.fetchMovieById(movieId)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre los elementos
    ) {



        Header()

        if (movie != null) {
            var selectedMovie by remember { mutableStateOf(movie!!) }

            Text("Detalles de la película")
            AsyncImage(
                model = selectedMovie!!.poster,
                contentDescription = selectedMovie?.title ?: "Título no disponible",
                modifier = Modifier
                    .height(200.dp)
                    .weight(1f)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 18.dp)
            ) {

                Row() {
                    Text(
                        text = selectedMovie!!.title ?: "Título no disponible",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 3,
                    )
                }

                Text(
                    text = selectedMovie!!.year ?: "Año desconocido",
                    style = MaterialTheme.typography.bodyLarge
                )

                Row() {
                    BackButton()

                    BookMarkButton(
                        onClick = { println("Añadir a favoritos") }
                    )
                }

            }

        } else {
            Text("La película es más nula.... anda, bonico, ve y haz el fetch a la película que quieres")
        }
    }
}
