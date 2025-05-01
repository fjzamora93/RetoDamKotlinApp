package com.unir.conexionapirest.data.model

data class Solicitud(
    val idSolicitud: Int? = null,
    var fecha: String,
    var archivo: String,
    var comentarios: String,
    val estado: Int,

    var vacante: Vacante,
    var user: UserModel,
)
