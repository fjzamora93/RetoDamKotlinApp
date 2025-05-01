package com.unir.conexionapirest.data.model

import java.util.Date

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: UserModel,
    val expiration: Date
)