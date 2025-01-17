package com.unir.conexionapirest.ui.screens

import android.app.Activity
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.ui.components.BackButton
import com.unir.conexionapirest.ui.components.BookMarkButton
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.Header
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.theme.Typography
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel
import kotlinx.coroutines.launch

@Composable
fun MovieDetailScreen(
    movieId: String,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity

    movieViewModel.fetchMovieById(movieId)
    LaunchedEffect(movieId) {
        movieViewModel.fetchMovieById(movieId)
    }
    val movie by movieViewModel.selectedMovie.observeAsState()


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
            Column {
                Header(
                    onMenuClick = {
                        coroutineScope.launch { drawerState.open() }
                    },
                )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    // If movie is null, show loading or empty state
                    if (movie != null) {
                        Text(
                            text = movie?.title ?: "No Title",
                            style = MaterialTheme.typography.titleLarge,
                            color = MiPaletaDeColores.Gold,
                            maxLines = 3,
                        )

                        AsyncImage(
                            model = movie!!.poster,  // Access directly
                            contentDescription = movie?.title ?: "Título no disponible",
                            modifier = Modifier
                                .height(400.dp)
                                .fillMaxWidth()
                                .padding(20.dp)
                        )
                        Text(
                            text = "Puntuación",
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie!!.rated,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        Text(
                            text = "Sinopsis",
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie!!.plot,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )

                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 8.dp),
                        )

                        Text(
                            text = "Actores",
                            style = Typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie!!.actors,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black
                        )







                    } else {
                        Column {
                            Text("Cargando detalles...")
                        }
                    }
                }
            }
        }}
    }
}