package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
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
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.flecha),
                contentDescription = "Flecha",
                modifier = Modifier
                    .size(15.dp)
                    .graphicsLayer { rotationZ = 90f }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Historial",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(150.dp))
        Image(
            painter = painterResource(id = R.drawable.historialogo),
            contentDescription = "Historial Logo",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Crea tu primer pedido.\nNo pierdas la oportunidad.",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(200.dp))
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
            Text("Reintentar", style = TextStyle(fontSize = 20.sp))
        }
    }
}