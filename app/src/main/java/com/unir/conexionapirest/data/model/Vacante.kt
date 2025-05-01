package com.unir.conexionapirest.data.model


data class Vacante(
    val idVacante: String,
    val nombre: String,
    val descripcion: String,
    val salario: Double,
    val estatus: String,
    val image: String,
    val active: Boolean
)
