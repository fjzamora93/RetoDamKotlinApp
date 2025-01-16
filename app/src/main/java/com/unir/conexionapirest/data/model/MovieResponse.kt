package com.unir.conexionapirest.data.model

import androidx.room.Entity

data class MovieResponse(
    val Search: List<Movie>,
    val Response: String
)
