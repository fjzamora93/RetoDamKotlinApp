package com.unir.conexionapirest.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.ui.components.BookMarkButton
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.SearchField
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel

@Composable
fun MainScreen(){
    val movieViewModel = MovieViewModel()

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Text(
            text = "Lista de película",
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
fun MovieList(
    modifier: Modifier = Modifier,
) {
    val movieViewModel = MovieViewModel()
    movieViewModel.fetchMovies()

    val movies by movieViewModel.movies.observeAsState(emptyList()) // Observa las películas como un State
    var filter by remember { mutableStateOf("") }

    // Pasar la función onMakeResearch al campo de búsqueda
    SearchField(
        onSearch = { searchText ->
            filter = searchText // Actualizamos el filtro con el texto ingresado
            movieViewModel.fetchMoviesByFilter(filter = searchText) // Realizamos la búsqueda con el texto actualizado
        }
    )

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
fun MovieItem(movie: Movie) {
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
