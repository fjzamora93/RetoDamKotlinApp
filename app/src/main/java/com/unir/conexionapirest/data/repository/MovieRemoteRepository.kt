package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.database.MovieApiService
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.data.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class MovieRemoteRepository @Inject constructor() {
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



    /**
     * Recordamos que la API OMDB no tiene un GET/All, y está limitado al número de resultados que devuelve.
     * SI supera ese límite, sencillamente no devuelve nada.
     * Para prevenirlo, ponemos una búsqueda por defecto en "main" (por elegir una palabra).
     *
     * En un caso real, esto lo intentaríamos gestionar de manera distinta.
     * Por ejemplo, ordenar por "más populares" y lanzar una petición con ese filtro limitado a 20 resultados
     * o crear un sistema de paginación para poder al menos manejarlo.
     * */
    fun fetchMovies(
        filter: String,
        onSuccess: (List<Movie>) -> Unit,
        onError: () -> Unit
    ) {

        // Establecemos un filtro por defecto si no hay palabra, buscará, por ejemplo "main"
        var filterCopy = if (filter.isBlank()) "main" else filter


        CoroutineScope(Dispatchers.IO).launch {
            val call = movieApiService.getMovies(searchTerm = filterCopy)
            val movies = call.body()
            println("Realizando búsqueda con filtro dentro del REpositorio: $movies y $filterCopy")


            withContext(Dispatchers.Main) {
                if (call.isSuccessful) {
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