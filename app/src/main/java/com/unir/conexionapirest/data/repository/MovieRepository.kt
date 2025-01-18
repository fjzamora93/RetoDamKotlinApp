package com.unir.conexionapirest.data.repository

import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.model.MovieResumen
import com.unir.conexionapirest.data.model.SearchFilter
import javax.inject.Inject


/** CLASE PARA INTEGRAR REPOSITORIO REMOTO Y LOCAL*/
class MovieRepository @Inject constructor (
    private val localMovieRepository: LocalMovieRepository,
    private val remoteMovieRepository: RemoteMovieRepository,
    //private val networkStatusChecker: NetworkStatusChecker
){

    fun getMovies(
        filter: SearchFilter,
        onSuccess: (List<MovieResumen>) -> Unit,
        onError: () -> Unit
    ){
        remoteMovieRepository.fetchMovies(
            filter,
            onSuccess,
            onError
        )
    }

    suspend fun saveMovie(movie: MovieResumen){
        localMovieRepository.insertFavMovie(movie)
    }

    suspend fun getAllFavMovies(): List<MovieResumen>{
        return localMovieRepository.getAllFavMovies()
    }

    suspend fun deleteMovie(movie: MovieResumen){
        localMovieRepository.deleteFavMovie(movie)

    }

    suspend fun getMovieById(
        movieId: String,
        onSuccess: (MovieDetail) -> Unit,
        onError: () -> Unit
    ){
        remoteMovieRepository.fetchMovieById(
            movieId,
            onSuccess,
            onError
        )
    }


}