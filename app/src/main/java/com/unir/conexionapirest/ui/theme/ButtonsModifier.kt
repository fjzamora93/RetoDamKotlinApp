package com.unir.conexionapirest.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object ButtonsModifier {

    val regularIcon = Modifier
        .size(56.dp)
        .background(MiPaletaDeColores.ParchmentLight, shape = CircleShape)
        .padding(12.dp)
        .border(
            width = 0.5.dp,
            color = MiPaletaDeColores.WoodenDark,
            shape = CircleShape)

    val navigationIcon = Modifier
        .size(40.dp)
        .background(MiPaletaDeColores.PurpleGrey80, shape = CircleShape)
        .padding(10.dp)



}