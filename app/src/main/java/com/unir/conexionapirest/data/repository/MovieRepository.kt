package com.unir.conexionapirest.data.repository

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



}