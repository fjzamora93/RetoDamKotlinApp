package com.unir.conexionapirest.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.conexionapirest.data.model.UserModel
import com.unir.conexionapirest.navigation.LocalAuthViewModel
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.layout.MainLayout
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel
import com.unir.conexionapirest.ui.viewmodels.UserState


@Composable
fun UserProfileScreen() {
    MainLayout(){
        Column(){
            UserProfileBody()
        }
    }
}

@Composable
fun UserProfileBody(
    viewModel: AuthViewModel = LocalAuthViewModel.current,
    navigation: NavigationViewModel = LocalNavigationViewModel.current
) {
    val userState by viewModel.userState.collectAsState()

    when (userState) {
        is UserState.Loading -> Column{ androidx.compose.material.CircularProgressIndicator() }
        is UserState.Success -> UserProfileDetail(
            user = (userState as UserState.Success).user,
        )
        is UserState.Error -> {
            navigation.navigate(ScreensRoutes.LoginScreen.route)
        }

        is UserState.LoggedOut -> navigation.navigate(ScreensRoutes.LoginScreen.route)

        else -> {

        }
    }
}



@Composable
fun UserProfileDetail(
    user: UserModel,
    viewModel: AuthViewModel = LocalAuthViewModel.current,
    navigation : NavigationViewModel = LocalNavigationViewModel.current
){
    val textColor: Color = Color.White
    Spacer(modifier = Modifier.height(200.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Text(
            text = "Bienvenido",
            style = MaterialTheme.typography.titleLarge,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = textColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${user.id}",
            style = MaterialTheme.typography.titleSmall,
            color = textColor
        )
        Text(
            text = "${user.name} ${user.surname}",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )
        Text(
            text = "${user.email}",
            style = MaterialTheme.typography.bodyMedium,
            color = textColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.logout()
                navigation.navigate(ScreensRoutes.LoginScreen.route)
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Cerrar sesión",
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Logout",
                fontSize = 16.sp,
            )
        }

    }
}


