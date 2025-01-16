package com.unir.conexionapirest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.database.MovieApiService
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val movieRepository = MovieRepository()

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    private val _error =MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

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
