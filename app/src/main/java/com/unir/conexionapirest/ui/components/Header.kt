package com.unir.conexionapirest.ui.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.NavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
) {
    val activity = LocalContext.current as Activity
    val navigationViewModel = LocalNavigationViewModel.current

    Column(
        modifier = modifier.padding(8.dp)
    ){

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Menú de la aplicación
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "open menu",
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onMenuClick() }
            )

            HomeButton()

            // Cerrar la aplicación
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close app",
                modifier = Modifier
                    .clickable { activity.finish() }
            )
        }

        HorizontalDivider(
            Modifier
                .background(Color(0xFFEEEEEE))
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}

