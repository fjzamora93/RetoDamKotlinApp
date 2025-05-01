package com.unir.conexionapirest.data.repository

import com.roleapp.auth.security.TokenManager
import com.unir.conexionapirest.data.model.Solicitud
import com.unir.conexionapirest.data.model.UserModel
import com.unir.conexionapirest.data.model.Vacante
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.ui.theme.AppStrings
import javax.inject.Inject

/**CAPA LÓGICA DE ACCESO A BASE DE DATOS REMOTA*/
class Repository @Inject constructor(
    private val apiService: ApiService,
    ) {


    suspend fun fetchVacantes(): Result<List<Vacante>> {
        return try {
            val response = apiService.getVacantes()

            if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
            }

            val vacantes = response.body()
            if (vacantes != null) {
                Result.success(vacantes)
            } else {
                // Handle the case where the body is null (though isSuccessful was true)
                Result.failure(Exception("Respuesta exitosa pero cuerpo vacío."))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
        }
    }

    suspend fun addSolicitud(
        solicitud: Solicitud,
    ): Result<Solicitud> {
        return try {
            val response = apiService.addSolicitud(solicitud)

            if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
            }

            val solicitud = response.body()
            if (solicitud != null) {
                Result.success(solicitud)
            } else {
                Result.failure(Exception("Respuesta exitosa pero cuerpo vacío."))
            }
        } catch (e: Exception) {
            Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
        }
    }



    suspend fun fetchSolicitudes(id: Int) : Result<List<Solicitud>> {
       return try {
           val response = apiService.getSolicitudes(id)
           if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
           }
           val solicitudes = response.body()
           if (solicitudes != null) {
               Result.success(solicitudes)
           } else {
               Result.failure(Exception("Respuesta exitosa pero cuerpo vacío."))
           }
       } catch (e: Exception) {
           Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
       }
    }


}