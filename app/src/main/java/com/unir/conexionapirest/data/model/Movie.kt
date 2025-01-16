package com.unir.conexionapirest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


// Ejemplo http://www.omdbapi.com/?i=tt3896198&apikey=f9c39ba3
// http://www.omdbapi.com/?apikey=f9c39ba3&s=lord&type=movie&plot=short&page=1

@Entity(tableName = "movieTable")
data class Movie(
    // LOs campos que aparecen en al lista
    @PrimaryKey(autoGenerate = false) val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,

    // Campos de los detalles
    @SerializedName("Plot") val plot: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released : String,
    @SerializedName("Runtime")  val runtime: String,
)
