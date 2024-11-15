package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peludosteam.ismarket.R

@Composable
fun WifiErrorScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()  // Ocupa todo el espacio disponible
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.nowifi),
                contentDescription = "Wifi error Logo",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.padding(top = 16.dp))

            Text(
                text = "Tu conexión a Internet no está \n" +
                        "disponible en este momento. \n" +
                        "Por favor, verifica tu conexión \n" +
                        "o intenta de nuevo.",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(34.dp))
            Button(
                onClick = {  },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFA4A0C),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp)
                    .height(55.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp))
            ) {
                Text("Reintentar" , style = TextStyle(fontSize = 20.sp))
            }
        }
    }
}
