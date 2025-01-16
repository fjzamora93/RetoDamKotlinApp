package com.unir.conexionapirest.data.database

import androidx.room.Dao
import com.unir.conexionapirest.data.model.Movie
import com.unir.conexionapirest.data.model.MovieDetail
import com.unir.conexionapirest.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

@Dao
interface MovieApiService {
    // URL DE EJEMPLO;:http://www.omdbapi.com/?apikey=f9c39ba3&s=lord&type=movie&plot=short&page=1
//    @GET("?apikey=f9c39ba3&s=lord&type=movie&plot=short&page=1")
//    suspend fun getMovies(): Response<MovieResponse>

    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apiKey: String = "f9c39ba3",
        @Query("s") searchTerm: String = "lord",
        @Query("type") type: String = "movie",
        @Query("plot") plot: String = "short",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMoviesByFilter(
        @Query("apikey") apiKey: String = "f9c39ba3",
        @Query("s") searchTerm: String,
        @Query("type") type: String = "",
        @Query("plot") plot: String = "",
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieById(
        @Query("apikey") apiKey: String = "f9c39ba3",
        @Query("i") movieId: String
    ): Response<MovieDetail>

}