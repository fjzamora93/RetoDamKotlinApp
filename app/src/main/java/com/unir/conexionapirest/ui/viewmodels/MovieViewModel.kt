package com.unir.conexionapirest.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.model.MovieResumen
import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.model.SearchFilter
import com.unir.conexionapirest.data.repository.LocalMovieRepository
import com.unir.conexionapirest.data.repository.MovieRepository
import com.unir.conexionapirest.data.repository.RemoteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<MovieDetail>() // Change to MovieDetail
    val selectedMovie: LiveData<MovieDetail>  = _selectedMovie //

    private val _movies = MutableLiveData<List<MovieResumen>>()
    val movies: LiveData<List<MovieResumen>> get() = _movies

    private val _favMovies = MutableLiveData<List<MovieResumen>>()
    val favMovies: LiveData<List<MovieResumen>> get() = _favMovies

    private val _error =MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    fun fetchMovieById(movieId: String) {
        viewModelScope.launch {
            movieRepository.getMovieById(
                movieId = movieId,
                onSuccess = { movie ->
                    _selectedMovie.value = movie
                    println("Película encontrada Y ACTUALIZADA en el ModelView: ${selectedMovie.value}")
                    _error.value = false
                },
                onError = {
                    _error.value = true
                }
            )
        }
    }


    fun fetchMovies(filter: SearchFilter = SearchFilter()) {

        //! IGNORAR ESTO. Solo es un ejemplo para devolver un valor por defecto cuando no hay parámetros de búsqueda
        val updatedFilter = filter.copy(
            title = if (filter.title.isBlank()) "main" else filter.title,
            year = if (filter.title == "main") "2024" else filter.year
        )

        println("Realizando búsqueda con filtro dentro del ViewMOdel: $filter")
        movieRepository.getMovies(
            filter = updatedFilter,
            onSuccess = { movieList ->
                _movies.value = movieList
                _error.value = false

            },
            onError = {
                _error.value = true
                println("Activando el error")

            }
        )
    }

    fun addMovieToFavorites(movie: MovieResumen) {
        viewModelScope.launch{
            movieRepository.saveMovie(movie)
        }
    }

    fun getFavMovies() {
        viewModelScope.launch{
            val movieList = movieRepository.getAllFavMovies()
            _favMovies.value = movieList
        }
    }

    fun removeFromFavorites(movie: MovieResumen) {
        viewModelScope.launch {
            movieRepository.deleteMovie(movie)
            val movieList = movieRepository.getAllFavMovies()
            _favMovies.value = movieList
        }
    }

    fun clearError(){
        _error.value = false
    }

}
