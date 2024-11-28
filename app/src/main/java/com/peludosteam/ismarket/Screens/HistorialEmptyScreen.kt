package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peludosteam.ismarket.R

@Composable
fun HistorialEmptyScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), // Eliminado padding vertical extra
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp), // Ajustado para mejor alineación
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.flecha),
                contentDescription = "Flecha",
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer { rotationZ = 90f }
                    .clickable {
                        navController.navigateUp()
                    }
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Historial",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(80.dp)) // Espacio entre cabecera y logo
        Image(
            painter = painterResource(id = R.drawable.historialogo),
            contentDescription = "Historial Logo",
            modifier = Modifier.size(200.dp) // Tamaño ajustado del logo
        )
        Spacer(modifier = Modifier.height(16.dp)) // Menos espacio
        Text(
            text = "No hay historial por ahora",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp)) // Espacio reducido
        Text(
            text = "Crea tu primer pedido.\nNo pierdas la oportunidad.",
            textAlign = TextAlign.Center,
            style = TextStyle(fontSize = 16.sp, color = Color.Gray),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(80.dp)) // Menos espacio antes del botón
        Button(
            onClick = { /* Acción para empezar pedido */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFA4A0C),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .height(50.dp) // Botón ligeramente más compacto
                .shadow(4.dp, shape = RoundedCornerShape(12.dp))
        ) {
            Text(
                text = "Empieza tu pedido",
                style = TextStyle(
                    fontSize = 18.sp, // Texto un poco más pequeño
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}