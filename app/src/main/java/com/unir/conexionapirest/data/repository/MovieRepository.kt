package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.database.MovieApiService
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.model.MovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MovieRepository @Inject constructor() {
    private val movieApiService: MovieApiService

    init {
        // Inicializamos Retrofit y el servicio
        val retrofit = getRetrofit()
        movieApiService = retrofit.create(MovieApiService::class.java)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Función para hacer la solicitud a la API
    fun fetchMovies(onSuccess: (List<Movie>) -> Unit, onError: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieApiService.getMovies()
            val movies = call.body()

            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    val movieList = movies?.Search ?: emptyList()
                    onSuccess(movieList)
                } else {
                    println("Error: ${call.errorBody()?.string()}")
                    onError()
                }
            }

        }
    }

    fun fetchByFilter(
        filter: String,
        onSuccess: (List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = movieApiService.getMoviesByFilter(searchTerm = filter)
            val movies = call.body()
            println("Realizando búsqueda con filtro dentro del REpositorio: $movies y $filter")


            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
                    val movieList = movies?.Search ?: emptyList()
                    onSuccess(movieList)
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
            println("JSON recibido: ${call.raw()}")

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