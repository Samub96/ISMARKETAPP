package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peludosteam.ismarket.R

@Composable
fun AddressScreen(navController: NavController) {
    val (selectedOption, setSelectedOption) = remember { mutableStateOf("Entrega a domicilio") }
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
            horizontalArrangement = Arrangement.Start // Alinea la imagen a la izquierda
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
                text = "Pedido",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Domicilio" ,
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Detalles de la dirección",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Start
            )
            val annotatedString = buildAnnotatedString {
                append("Cambiar dirección")
            }
            ClickableText(
                text = annotatedString,
                onClick = {
                    navController.navigate("pantallaDestino")
                },
                style = TextStyle(
                    color = Color(0xFFFA4A0C),
                    fontSize = 16.sp,
                )
            )
        }
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                .height(150.dp)
                .width(300.dp),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(text = "Universidad ICESI",
                    style = TextStyle(fontSize = 20.sp),
                    fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp))
                HorizontalDivider(
                    color = Color.Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(200.dp)
                )
                Spacer(modifier = Modifier
                    .height(8.dp)
                )
                Text(text = "Salon 101M",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp))
                Text(text = "3177043737",
                    style = TextStyle(fontSize = 20.sp),
                    modifier = Modifier
                        .padding(8.dp))
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Método de entrega",
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.Black,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                    .height(200.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == "Entrega a domicilio",
                            onClick = { setSelectedOption("Entrega a domicilio") },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFA4A0C))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Entrega a domicilio",
                            style = TextStyle(fontSize = 18.sp),
                            color = Color.Black
                        )
                    }
                    HorizontalDivider(
                        color = Color.Gray,
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedOption == "Recogerlo en la tienda",
                            onClick = { setSelectedOption("Recogerlo en la tienda") },
                            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFA4A0C))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Recogerlo en la tienda",
                            style = TextStyle(fontSize = 18.sp),
                            color = Color.Black
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row ( modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically){
                Text(text = "Total", style = TextStyle(fontSize = 20.sp),
                )
                Text(text = "1200", style = TextStyle(fontSize = 20.sp),
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Button(
                onClick = { navController.navigate("login") },
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
                Text("Proceder al pago" , style = TextStyle(fontSize = 20.sp))
            }

        }
    }
}
