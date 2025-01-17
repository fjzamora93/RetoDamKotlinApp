package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.database.MovieDao
import com.unir.conexionapirest.data.model.Movie
import javax.inject.Inject



class LocalMovieRepository @Inject constructor(
    private val movieDao: MovieDao
) {

    suspend fun getAllFavMovies(): List<Movie> {
        return movieDao.getAllMovies()
    }

    suspend fun insertFavMovie(movie: Movie) {
        movieDao.insert(movie)
    }

    suspend fun deleteFavMovie(movie: Movie) {
        movieDao.delete(movie)
    }

    suspend fun isFavMovie(movieId: String): Boolean {
        return movieDao.getMovieById(movieId) != null
    }
}
