package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.database.MovieDao
import com.unir.conexionapirest.data.model.MovieResumen
import javax.inject.Inject


/**CAPA LÓGICA DE ACCESO A BASE DE DATOS LOCAL*/
class LocalMovieRepository @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun getAllFavMovies(): List<MovieResumen> {
        return movieDao.getAllMovies()
    }

    suspend fun insertFavMovie(movie: MovieResumen) {
        movieDao.insert(movie)
    }

    suspend fun deleteFavMovie(movie: MovieResumen) {
        movieDao.delete(movie)
    }

    suspend fun isFavMovie(movieId: String): Boolean {
        return movieDao.getMovieById(movieId) != null
    }
}
