package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.database.MovieApiService
import com.unir.conexionapirest.data.model.MovieResumen
import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.model.SearchFilter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**CAPA LÓGICA DE ACCESO A BASE DE DATOS REMOTA*/
class RemoteMovieRepository @Inject constructor(
    private val movieApiService: MovieApiService
) {


    fun fetchMovies(
        filter: SearchFilter,
        onSuccess: (List<MovieResumen>) -> Unit,
        onError: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieApiService.getMovies(
                searchTerm = filter.title,
                year = filter.year
            )
            val movies = call.body()
            println("Realizando búsqueda con filtro dentro del REpositorio: $movies y $filter")
            withContext(Dispatchers.Main) {
                if (call.isSuccessful && filter.title != "error") {
                    val movieList = movies?.Search ?: emptyList()
                    val sortedMovieList =  movieList.sortedByDescending { it.year.toInt() }
                    onSuccess(sortedMovieList)
                } else {
                    println("Error: ${call.errorBody()?.string()}")
                    onError()
                }
            }

        }
    }

    fun fetchMovieById(
        movieId: String,
        onSuccess: (MovieDetail) -> Unit,
        onError: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieApiService.getMovieById(movieId = movieId)
            val movie = call.body()
            println("Realizando búsqueda con filtro dentro del REpositorio: $movie y $movieId")
            withContext(Dispatchers.Main) {
                if (call.isSuccessful && movie != null) {
                    onSuccess(movie)
                } else {
                    println("Error: ${call.errorBody()?.string()}")
                    onError()
                }
            }

        }
    }



}