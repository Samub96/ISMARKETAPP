package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peludosteam.ismarket.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAddressScreen(navController: NavController) {
    val (selectedOption, setSelectedOption) = remember { mutableStateOf("Entrega a domicilio") }
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }
    var thirdInput by remember { mutableStateOf("") }
    var savedInfo by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Ubicación",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Ubicación",
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.torre),
                contentDescription = "Imagen de torre",
                modifier = Modifier
                    .size(500.dp)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TextField(
                    value = firstInput,
                    onValueChange = { firstInput = it },
                    label = { Text("Edificio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(16.dp)
                )

                TextField(
                    value = secondInput,
                    onValueChange = { secondInput = it },
                    label = { Text("Piso") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = thirdInput,
                    onValueChange = { thirdInput = it },
                    label = { Text("Salon") },
                    modifier = Modifier
                        .fillMaxWidth()

                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        savedInfo =
                            "Datos guardados:\n1: $firstInput\n2: $secondInput\n3: $thirdInput"
                    },
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
                    Text(text = "Guardar Ubicación",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }
                TODO("Crear una notificacion que se ha guardado la info")
            }
        }
    }
}