package com.unir.conexionapirest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.database.MovieApiService
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<MovieDetail>() // Change to MovieDetail
    val selectedMovie: LiveData<MovieDetail>  = _selectedMovie //

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _error =MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun fetchMovieById(movieId: String) {
        viewModelScope.launch {
            println("Película encontrada Y ACTUALIZADA en el ModelView: ${selectedMovie.value}")

            movieRepository.fetchMovieById(
                movieId = movieId,
                onSuccess = { movie ->
                    _selectedMovie.value = movie  // Now it's a MovieDetail
                    println("Película encontrada Y ACTUALIZADA en el ModelView: ${selectedMovie.value}")
                    _error.value = false  // Si todo va bien, resetear el error
                },
                onError = {
                    _error.value = true  // Aquí manejas el error
                }
            )
        }
    }

    // Función para hacer la solicitud a la API a través del repositorio
    fun fetchMovies() {
        movieRepository.fetchMovies(
            onSuccess = { movieList ->
                _movies.value = movieList
            },
            onError = {
                _error.value = true
            }
        )
    }

    fun fetchMoviesByFilter(filter: String) {
        println("Realizando búsqueda con filtro dentro del ViewMOdel: $filter")
        movieRepository.fetchByFilter(
            filter = filter,
            onSuccess = { movieList ->
                _movies.value = movieList
            },
            onError = {
                _error.value = true
            }
        )
    }



}
