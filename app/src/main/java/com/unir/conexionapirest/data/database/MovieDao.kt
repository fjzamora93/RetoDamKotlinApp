package com.unir.conexionapirest.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unir.conexionapirest.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movieTable")
    suspend fun getAllMovies(): List<Movie>

    @Query("SELECT * FROM movieTable WHERE imdbID = :movieId LIMIT 1")
    suspend fun getMovieById(movieId: String): Movie?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)
}
