package com.unir.conexionapirest.data.repository

import android.util.Log
import com.roleapp.auth.security.TokenManager

import com.roleapp.auth.data.model.RefreshTokenRequest
import com.roleapp.auth.domain.repository.AuthRepository
import com.unir.conexionapirest.data.model.LoginRequest
import com.unir.conexionapirest.data.model.UserModel
import com.unir.conexionapirest.data.service.AuthApiService
import java.io.IOException
import javax.inject.Inject


class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApiService,
    private val tokenManager: TokenManager,
) : AuthRepository {


    override suspend fun login(email: String, password: String): Result<UserModel> {
        return try {
            val response = api.login(LoginRequest(email, password))
            if (response.code() == 200 && response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)

                Result.success(loginResponse.user)
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("${errorBody}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    // TODO: Mejorar el Sign UP para que haga un autologin (coordinarlo con el Backend)
    // TODO: IMplementar el Doble Factor  al registrase (o mandar un email o algo)
    override suspend fun signup(email: String, password: String): Result<UserModel> {
        return try {

            val newUserRequest = LoginRequest(email, password)
            val response = api.signup(newUserRequest)

            if (response.isSuccessful && response.body() != null) {
                val loginResponse = response.body()!!

                // Guardar tokens
                tokenManager.saveAccessToken(loginResponse.accessToken)
                tokenManager.saveRefreshToken(loginResponse.refreshToken)

                Result.success(loginResponse.user)
            } else {
                val errorBody = response.errorBody()?.string()
                Result.failure(Exception("$errorBody"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // TODO: Aunque en el front se eliminan los tokens en el logout (aquí ya no hay que hacer nada),
    //  es necesario reforzar la seguridad en el backend (crear lista negra)
    override suspend fun logout(): Result<Unit> {
        return try {
            val response = api.logoutUser()

            if (response.isSuccessful) {
                tokenManager.clearTokens()
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error en logout: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    // Obtener usuario (usando Access Token)
    override suspend fun getUser(): Result<UserModel> {
        return try {
            // Intentar obtener el token de acceso
            val token = tokenManager.getAccessToken()

            if (token.isNullOrEmpty()) {
                return Result.failure(Exception("No se encontró el usuario local ni el token"))
            }

            // Si hay token, intentar obtener el usuario desde la API
            val response = api.getUser("Bearer $token")

            if (response.isSuccessful && response.body() != null) {
                return Result.success(response.body()!!)
            } else {
                return Result.failure(Exception("Error obteniendo usuario desde la API y no hay usuario local"))
            }
        } catch (e: Exception) {
            // Manejo de cualquier excepción que ocurra durante el proceso
            println("Error: ${e.message}")
            return Result.failure(e)
        }
    }



}


