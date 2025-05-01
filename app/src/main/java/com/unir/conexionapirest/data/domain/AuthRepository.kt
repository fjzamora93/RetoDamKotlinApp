package com.roleapp.auth.domain.repository

import com.unir.conexionapirest.data.model.UserModel


interface AuthRepository {
    suspend fun getUser(): Result<UserModel>
    suspend fun login(email: String, password: String): Result<UserModel>
    suspend fun signup(email: String, password: String): Result<UserModel>
    suspend fun logout(): Result<Unit>
}