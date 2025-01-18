package com.unir.conexionapirest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


// Ejemplo http://www.omdbapi.com/?i=tt3896198&apikey=f9c39ba3
// http://www.omdbapi.com/?apikey=f9c39ba3&s=lord&type=movie&plot=short&page=1

/**
 *Por cómo funciona esta API, no es lo mismo recuperar una sola Movie (donde hay muchos más campos)
 * que recuperar un listado (el List<Movie>) donde únicamente se recuperan los campos que aparecen aquí abajo.
 * */
@Entity(tableName = "movieTable")
data class MovieResumen(
    // LOs campos que aparecen en al lista
    @PrimaryKey(autoGenerate = false) val imdbID: String,
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Poster") val poster: String,

) {

}

