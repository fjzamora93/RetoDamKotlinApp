package com.unir.conexionapirest.data.service

import androidx.room.Dao
import com.unir.conexionapirest.data.model.ApiResponse
import com.unir.conexionapirest.data.model.ItemDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/** Retrofit, con la URL está configurado en la INyección de dependencias URL DE EJEMPLO;:http://peticiones.online/images/products/ */
@Dao
interface ApiService {

    @GET("products/")
    suspend fun getItems(): Response<ApiResponse>


    @GET("products/{id}/")
    suspend fun getMovieById(
        @Path("id") id: String
    ): Response<ItemDetails>

}