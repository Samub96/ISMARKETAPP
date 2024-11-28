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
import androidx.compose.material3.Scaffold
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
fun OffertErrorScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 0.dp), // Sin padding vertical adicional
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp), // Separación controlada
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.flecha),
                        contentDescription = "Flecha",
                        modifier = Modifier
                            .size(20.dp) // Tamaño ajustado
                            .graphicsLayer { rotationZ = 90f }
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Ofertas",
                        style = TextStyle(fontSize = 20.sp),
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(50.dp)) // Espacio controlado

                Image(
                    painter = painterResource(id = R.drawable.ventasflash),
                    contentDescription = "Offert Logo",
                    modifier = Modifier.size(200.dp) // Tamaño del logo ajustado
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre logo y texto principal

                Text(
                    text = "No tienes ofertas disponibles",
                    style = TextStyle(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(12.dp)) // Espacio reducido

                Text(
                    text = "Por el momento no tienes ofertas disponibles " +
                            "para ti, pero no te preocupes, en cuanto tengamos " +
                            "ofertas disponibles te notificaremos.",
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    )
}