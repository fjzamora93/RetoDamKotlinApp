
package com.unir.conexionapirest.data.service

import com.roleapp.auth.data.model.RefreshTokenRequest
import com.unir.conexionapirest.data.model.LoginRequest
import com.unir.conexionapirest.data.model.LoginResponse
import com.unir.conexionapirest.data.model.UserModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


// TODO : AuthApiService está mezclando al Usuario con la autentificación. Separar competencias en dos servicios distintos.

interface AuthApiService {


    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh-token")
    suspend fun refreshAccessToken(@Body request: RefreshTokenRequest): Response<LoginResponse>

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/logout")
    suspend fun logoutUser(): Response<Void>

    @GET("user/me")
    suspend fun getUser(@Header("Authorization") token: String): Response<UserModel>

}