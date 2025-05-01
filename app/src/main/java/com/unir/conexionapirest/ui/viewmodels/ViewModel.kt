package com.unir.conexionapirest.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.model.Solicitud
import com.unir.conexionapirest.data.model.UserModel
import com.unir.conexionapirest.data.model.Vacante
import com.unir.conexionapirest.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _solicitudes = MutableStateFlow<List<Solicitud>>(emptyList())
    val solicitudes: MutableStateFlow<List<Solicitud>> get() = _solicitudes


    private val _vacantes = MutableStateFlow<List<Vacante>>(emptyList())
    val vacantes: MutableStateFlow<List<Vacante>> get() = _vacantes

    private val _filteredVacantes = MutableStateFlow<List<Vacante>>(emptyList())
    val filteredVacantes: MutableStateFlow<List<Vacante>> get() = _filteredVacantes

    private val _error =MutableStateFlow<String?>(null)
    val error: MutableStateFlow<String?> get() = _error

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading


    init {
        fetchVacantes()
    }

    fun fetchVacantes() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchVacantes()
            result.onSuccess {
                _vacantes.value = it
                _error.value = null
            }
            result.onFailure {
                _error.value = it.message
            }
            _filteredVacantes.value = vacantes.value

            _loading.value = false
        }
    }

    fun fetchSolicitudes(userId: Int) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.fetchSolicitudes(userId)
            result.onSuccess {
                _solicitudes.value = it
                _error.value = null
            }
            result.onFailure {
                _error.value = it.message
            }
            _loading.value = false
        }
    }


    fun addSolicitud(
        solicitud: Solicitud,
    ) {
        viewModelScope.launch {
            Log.d("Solicitud", solicitud.user.id.toString())
            repository.addSolicitud(solicitud)
        }
    }



    fun filter(filter: String){
        if (filter != "") {
            _filteredVacantes.value = vacantes.value.filter {
                it.nombre.contains(filter, ignoreCase = true)
            }
            println(filteredVacantes.value)
        } else {
            _filteredVacantes.value = vacantes.value
        }
    }

    fun clearError(){
        _error.value = null
    }

}
