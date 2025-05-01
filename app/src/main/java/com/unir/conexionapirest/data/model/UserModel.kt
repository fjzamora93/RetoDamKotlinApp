package com.unir.conexionapirest.data.model

data class UserModel(
    val id: Long? = null,
    var name: String,
    var surname: String,
    var email: String,
    var password: String,
    var enabled: Int,
    var solicitudes: List<Solicitud> = mutableListOf()
)
