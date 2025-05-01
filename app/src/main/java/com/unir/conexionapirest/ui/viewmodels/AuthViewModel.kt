package com.unir.conexionapirest.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unir.conexionapirest.data.SharedPrefsHelper
import com.unir.conexionapirest.data.model.UserModel
import com.unir.conexionapirest.data.repository.AuthRepositoryImpl
import com.unir.conexionapirest.ui.viewmodels.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepositoryImpl,
) : ViewModel() {

    // Estado de la autenticación: LoggedOut, IDLE, LOADING, SUCCESS, ERROR
    private val _userState = MutableStateFlow<UserState>(UserState.Idle)
    val userState: StateFlow<UserState> = _userState

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage



    // Lanzar el autologin al inicializar el ViewModel
    init {
        if (_userState.value != UserState.LoggedOut) {
            //autologin()
        }
    }

    fun login(email: String, password: String, context: Context) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result: Result<UserModel> = authRepository.login(email, password)
            _userState.value = result.fold(
                onSuccess = {
                    // Guardar en SharedPreferences
                    SharedPrefsHelper(context).saveUser(it)
                    UserState.Success(it)
                },
                onFailure = {
                    UserState.Error(it.message ?: "Error en login")
                }
            )

            if (userState.value is UserState.Error) {
                val errorMessage = (userState.value as UserState.Error).message
                _errorMessage.value = errorMessage
                println("Error en login: $errorMessage")
            }
        }
    }

//    fun autologin() {
//        _userState.value = UserState.Loading
//        viewModelScope.launch {
//            val result: Result<UserModel>  = authRepository.autoLogin()
//            result.onSuccess { it ->
//                _userState.value = UserState.Success(it)
//            }.onFailure{
//                _userState.value = UserState.Error(it.message ?: "Error en autologin")
//            }
//
//        }
//    }

    fun signup(email: String, password: String, confirmPassword: String, context: Context) {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result: Result<UserModel>  = authRepository.signup(email, password)

            result.onSuccess { it ->
                _userState.value = UserState.Success(it)
            }.onFailure{
                _userState.value = UserState.Error(it.message ?: "Error registrando usuario")
            }

            if (userState.value is UserState.Error) {
                val errorMessage = (userState.value as UserState.Error).message
                _errorMessage.value = errorMessage
                println("Error en signup: $errorMessage")
            }

            if (userState.value is UserState.Success) {
                login(email, password, context)
            }

        }
    }

    fun logout() {
        _userState.value = UserState.Loading
        viewModelScope.launch {
            val result = authRepository.logout()
            _userState.value = result.fold(
                onSuccess = { UserState.LoggedOut },
                onFailure = { UserState.Error(it.message ?: "Error en logout") }
            )
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null

    }

}