package com.unir.conexionapirest.data.repository

import android.util.Log
import com.unir.conexionapirest.data.model.ItemDetails
import com.unir.conexionapirest.data.service.ApiService
import com.unir.conexionapirest.data.model.ItemResumen
import com.unir.conexionapirest.ui.theme.AppStrings
import javax.inject.Inject

/**CAPA LÓGICA DE ACCESO A BASE DE DATOS REMOTA*/
class Repository @Inject constructor(
    private val apiService: ApiService
) {


    suspend fun fetchData(): Result<List<ItemResumen>> {
        return try {
            val response = apiService.getItems()

            if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
            }

            val itemsResponse = response.body()
            if (itemsResponse?.results.isNullOrEmpty()) {
                return Result.failure(Exception("No se encontraron resultados para la búsqueda"))
            }

            Log.v( "Respuesta: ","$itemsResponse")
            val sortedList = itemsResponse!!.results.sortedByDescending { it.price.toInt() }

            Result.success(sortedList)
        } catch (e: Exception) {
            Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
        }
    }



    suspend fun fetchById(id: String) : Result<ItemDetails> {
       return try {
           val response = apiService.getMovieById(id)
           if (!response.isSuccessful) {
                return Result.failure(Exception("Error en la respuesta del servidor: ${response.errorBody()?.string()}"))
           }
           val itemResponse = response.body()
               ?: return Result.failure(Exception("NO hay coincidencias de búsqueda"))
           Result.success(itemResponse)
       } catch (e: Exception) {
           Result.failure(Exception("Algo salió mal: ${e.message}, imposible conectar a ${AppStrings.BASE_URL}"))
       }
    }




}