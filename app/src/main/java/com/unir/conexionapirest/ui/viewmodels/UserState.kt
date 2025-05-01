package com.unir.conexionapirest.ui.viewmodels

import com.unir.conexionapirest.data.model.UserModel

/**
 * Una sealed class (clase sellada) en Kotlin es una forma de restringir un tipo a un conjunto fijo de subtipos.
 * Piensa en ella como una especie de "enum mejorado" que puede tener clases con propiedades y lógica, no solo constantes.
 *
 * Esto representa todos los estados posibles de un usuario dentro de tu app. ¿Qué significa cada uno?
 * */
sealed class UserState {
    object Idle : UserState()
    object Loading : UserState()
    data class Success(val user: UserModel) : UserState()
    data class Error(val message: String) : UserState()
    object LoggedOut : UserState()
    object Deleted : UserState()
}