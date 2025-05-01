package com.unir.conexionapirest.ui.screens

import android.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.unir.conexionapirest.data.model.Solicitud
import com.unir.conexionapirest.data.model.Vacante
import com.unir.conexionapirest.navigation.LocalAuthViewModel
import com.unir.conexionapirest.navigation.LocalNavigationViewModel
import com.unir.conexionapirest.navigation.ScreensRoutes
import com.unir.conexionapirest.ui.components.CustomHorizontalDivider
import com.unir.conexionapirest.ui.components.DetailButton
import com.unir.conexionapirest.ui.components.ErrorSnackBar
import com.unir.conexionapirest.ui.components.SearchField
import com.unir.conexionapirest.ui.layout.MainLayout
import com.unir.conexionapirest.ui.theme.AppStrings
import com.unir.conexionapirest.ui.viewmodels.AuthViewModel
import com.unir.conexionapirest.ui.viewmodels.UserState
import com.unir.conexionapirest.ui.viewmodels.ViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun VacantesScreen(){

    MainLayout(){
        Column(){
            VacanteList()
        }
        ErrorSnackBar()
    }
}


@Composable
fun VacanteList(
    viewModel: ViewModel = hiltViewModel()
) {
    val itemsList by viewModel.filteredVacantes.collectAsState()
    val isLoading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    SearchField()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = Color.Yellow
            )
        } else if (itemsList.isEmpty()){
            Text(
                text = error?: "No hay resultados de búsqueda",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        } else {
            itemsList.forEach {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ){
                    ItemResumen(item = it)
                }
                CustomHorizontalDivider()
            }

        }
    }
}


@Composable
fun ItemResumen(
    item: Vacante,
    viewModel: ViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = LocalAuthViewModel.current,
) {
    val userState by authViewModel.userState.collectAsState()
    val user = (userState as UserState.Success).user

    var isAddingComentario by remember { mutableStateOf(false) }
    var comentarioText by remember { mutableStateOf("") }

    val fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))

    var solicitud by remember {
        mutableStateOf(
            Solicitud(
                fecha = fechaActual,
                comentarios = "",
                archivo = "documentos adjuntos",
                estado = 1,
                user = user,
                vacante = item,
            )
        )
    }

    // Layout del ítem
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_menu_today),
            contentDescription = item.nombre,
            modifier = Modifier
                .height(100.dp)
                .weight(1f)
        )

        Column(
            modifier = Modifier
                .weight(2.5f)
                .fillMaxHeight()
                .padding(top = 18.dp)
        ) {
            Text(
                text = item.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 3,
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${AppStrings.INFO} : ${item.salario}",
                style = MaterialTheme.typography.labelLarge,
            )
        }

        IconButton(
            onClick = { isAddingComentario = true },
            modifier = Modifier
                .align(Alignment.CenterVertically)

                .size(48.dp) // aumenta el tamaño total del botón
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }

    }

    // Diálogo para comentario
    if (isAddingComentario) {
        AlertDialog(
            onDismissRequest = { isAddingComentario = false },
            title = { Text("Añadir comentario") },
            text = {
                OutlinedTextField(
                    value = comentarioText,
                    onValueChange = {
                        comentarioText = it
                        solicitud = solicitud.copy(comentarios = it)
                    },
                    label = { Text("Comentario") },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.addSolicitud(solicitud)
                    isAddingComentario = false
                    comentarioText = ""
                }) {
                    Text("Enviar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isAddingComentario = false
                    comentarioText = ""
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
