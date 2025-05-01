package com.unir.conexionapirest.data.service

import androidx.room.Dao
import com.unir.conexionapirest.data.model.Solicitud
import com.unir.conexionapirest.data.model.Vacante
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/** Retrofit, con la URL está configurado en la INyección de dependencias URL DE EJEMPLO;:http://peticiones.online/images/products/ */
@Dao
interface ApiService {

    @GET("vacantes")
    suspend fun getVacantes(): Response<List<Vacante>>

    @GET("solicitudes/{id}")
    suspend fun getSolicitudes(@Path("id") userId: Int): Response<List<Solicitud>>


    @POST("solicitudes")
    suspend fun addSolicitud(@Body solicitud: Solicitud): Response<Solicitud>


}