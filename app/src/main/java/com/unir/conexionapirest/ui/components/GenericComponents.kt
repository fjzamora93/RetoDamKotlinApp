package com.unir.conexionapirest.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.theme.MiPaletaDeColores
import com.unir.conexionapirest.ui.viewmodels.MovieViewModel

@Composable
fun CustomHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = MiPaletaDeColores.Bronze,
    thickness: Dp = 4.dp,
    startIndent: Dp = 0.dp
) {
    HorizontalDivider(
        modifier = modifier,
        color = color,
        thickness = thickness,
    )
}

@Composable
fun BookMarkButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp) // Tamaño más grande para mejorar la interacción
            .background(MiPaletaDeColores.ParchmentLight, shape = CircleShape) // Fondo con forma circular
            .padding(12.dp) // Relleno para que el ícono no quede pegado al borde
    ) {
        Icon(
            imageVector = Icons.Default.Favorite, // Ícono de favoritos
            contentDescription = "Add to Favorites",
            tint = MiPaletaDeColores.BloodRed // Color del ícono ajustado al tema
        )
    }
}

@Composable
fun DetailButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(56.dp) // Tamaño grande
            .background(MiPaletaDeColores.ParchmentLight, shape = CircleShape) // Fondo con color primario
            .padding(12.dp)
            .border(
                width = 0.5.dp,
                color = MiPaletaDeColores.WoodenDark,
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = Icons.Default.RemoveRedEye, // Ícono de ojo
            contentDescription = "View Details",
            tint = MiPaletaDeColores.LeatherAged
        )
    }
}




@Composable
fun SearchField(
    onSearch: (String) -> Unit,
) {
    var searchText by remember { mutableStateOf("") } // Usamos esta variable para manejar el texto internamente
    Row(
        modifier = Modifier
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically

    ){
        TextField(
            value = searchText,
            onValueChange = {
                searchText = it
                println(searchText)
            },
            label = { Text("Search") },
            modifier = Modifier
                .weight(8f)
                .padding(8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(MiPaletaDeColores.Bronze),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MiPaletaDeColores.Gold
                )
            },
            singleLine = true // Para que el campo de texto no se expanda verticalmente
        )

        SendButton(
            modifier = Modifier
                .weight(2f),
            onClick = {
                onSearch(searchText) // Llamamos a onSearch cuando se hace clic, pasando el texto actualizado
                println("Mandando petición contra la base de datos con $searchText")
            }
        )
        ClearButton(
            modifier = Modifier
                .weight(2f),
            onClick = {
                searchText = ""
                println("Formulario limpiado")
            }
        )
    }

}

@Composable
fun SendButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier
            .size(40.dp) // Tamaño más grande para mejorar la interacción
            .background(MiPaletaDeColores.ParchmentLight, shape = CircleShape)
            .border(
                width = 1.dp,
                color = MiPaletaDeColores.WoodenDark,
                shape = CircleShape
            ),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Send, // Icono de flecha de enviar
            contentDescription = "Send", // Descripción del icono
            tint = MiPaletaDeColores.Gold // Puedes ajustar el color si es necesario
        )
    }
}

@Composable
fun ClearButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
){
    IconButton(
        modifier = modifier
            .size(40.dp)
            .background(MiPaletaDeColores.ParchmentLight, shape = CircleShape)
            .border(
                width = 0.5.dp,
                color = MiPaletaDeColores.BloodRed,
                shape = CircleShape
            ),
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "Clear",
            tint = MiPaletaDeColores.BloodRed
        )
    }
}

@Composable
fun BackButton() {
    val navigationViewModel = LocalNavigationViewModel.current

    IconButton(
        onClick = { navigationViewModel.goBack() },
        modifier = Modifier
            .size(48.dp) // Tamaño del botón
            .clip(CircleShape) // Forma circular
            .background(MiPaletaDeColores.Bronze) // Fondo atractivo
            .padding(8.dp) // Espaciado interno
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Flecha hacia atrás
            contentDescription = "Go Back", // Descripción para accesibilidad
            tint = MiPaletaDeColores.Gold, // Color del ícono
            modifier = Modifier.size(24.dp) // Tamaño del ícono
        )
    }
}


@Composable
fun HomeButton() {
    val navigationViewModel = LocalNavigationViewModel.current

    IconButton(
        onClick = { navigationViewModel.navigate(ScreensRoutes.MainScreen.route) },
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(MiPaletaDeColores.Bronze)
            .padding(8.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Go Back",
            tint = MiPaletaDeColores.ParchmentDark,
            modifier = Modifier.size(24.dp)
        )
    }
}
