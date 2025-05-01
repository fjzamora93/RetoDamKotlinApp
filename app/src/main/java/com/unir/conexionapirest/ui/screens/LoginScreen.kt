package com.unir.conexionapirest.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.conexionapirest.navigation.LocalAuthViewModel
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.ui.layout.MainLayout
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel
import com.unir.conexionapirest.ui.viewmodels.UserState



@Composable
fun LoginScreen() {
    MainLayout(){
        Column(){
            LoginBody()
        }
    }
}



@Composable
fun LoginBody(
    authViewModel: AuthViewModel = LocalAuthViewModel.current,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current,
) {
    var email by remember { mutableStateOf("test@test.com") }
    var password by remember { mutableStateOf("1234") }
    val context = LocalContext.current

    var showRegister by remember { mutableStateOf(false) }
    val textColor: Color  =  Color.White
    val secondTextColor: Color  = Color.Gray
    val userState by authViewModel.userState.collectAsState()

    val errorMessage by authViewModel.errorMessage.collectAsState()

    var isLoggedIn by remember { mutableStateOf(false) }


    LaunchedEffect(showRegister) {
        authViewModel.clearErrorMessage()
    }

    LaunchedEffect(userState) {
        when (userState) {

            is UserState.LoggedOut -> {
                Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
            }

            is UserState.Success -> {
                //navigationViewModel.navigate(ScreensRoutes.UserScreen.route)
                isLoggedIn = true
            }

            is UserState.Deleted -> {
                Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
            }

            else -> {
                //navigationViewModel.navigate(ScreensRoutes.LoginScreen.route)

            }

        }

    }

    if (isLoggedIn){
        UserProfileBody()
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = if (showRegister) "Crear Cuenta" else "Iniciar Sesión",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = textColor
            )
            Spacer(modifier = Modifier.height(16.dp))


            errorMessage?.let {
                Text(
                    text = it,  // El mensaje de error
                    color = Color.Red,  // Color del mensaje
                    style = MaterialTheme.typography.labelMedium,  // Estilo del texto (puedes cambiarlo)
                    modifier = Modifier.padding(start = 16.dp) // Padding para que no esté pegado al borde
                )
            }

            if (userState is UserState.Loading) {
                CircularProgressIndicator()
            }

            Spacer(modifier = Modifier.height(16.dp))


            TextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                value = email,
                onValueChange = { email = it },
                label = { Text("Email", color = secondTextColor) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(textColor = textColor),
                isError = errorMessage != null,
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña", color = secondTextColor) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(textColor = textColor),
                isError = errorMessage != null,

                )


            if (showRegister) {
                var confirmPassword by remember { mutableStateOf("") }

                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirmar Contraseña", color = secondTextColor) },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(textColor = textColor),
                    isError = errorMessage != null,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { authViewModel.signup(email, password, confirmPassword, context) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                ) {
                    Text(text = "Registrarse", fontSize = 16.sp, color = Color.White)
                }
            } else {
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { authViewModel.login(email, password, context) },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                ) {
                    Text(text = "Iniciar sesión", fontSize = 16.sp, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Divider(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "¿Eres nuevo?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = secondTextColor
            )

            TextButton(onClick = { showRegister = !showRegister }) {
                Text(
                    text = if (showRegister) "Volver al login" else "Crear una cuenta",
                    fontSize = 16.sp,
                    color = textColor
                )
            }
        }
    }
}

