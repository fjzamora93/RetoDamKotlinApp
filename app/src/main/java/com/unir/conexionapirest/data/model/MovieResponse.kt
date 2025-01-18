package com.unir.conexionapirest.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("Search") val Search: List<MovieResumen>?,
    @SerializedName("Response") val Response: String?
){

}

