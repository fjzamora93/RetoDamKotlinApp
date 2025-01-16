package com.unir.conexionapirest.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val Search: List<Movie>?,
    @SerializedName("Response") val Response: String?
){

}

