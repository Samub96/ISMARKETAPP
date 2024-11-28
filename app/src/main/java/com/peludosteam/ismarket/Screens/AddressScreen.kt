package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.peludosteam.ismarket.viewmode.AddressViewModel

@Composable
fun AddressScreen(navController: NavController, viewModel: AddressViewModel) {
    val selectedOption = viewModel.deliveryMethod
    val addressDetails = viewModel.addressDetails
    val productPrice = viewModel.productPrice

    LaunchedEffect(true) {
        viewModel.listenForAddressUpdates()
        viewModel.listenForProductPrice()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

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
                            .clickable {
                                navController.popBackStack()
                            }
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Pedido",
                        style = TextStyle(fontSize = 22.sp),
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Domicilio",
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Detalles de la dirección",
                        style = TextStyle(fontSize = 20.sp),
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                    val annotatedString = buildAnnotatedString { append("Cambiar dirección") }
                    ClickableText(
                        text = annotatedString,
                        onClick = { navController.navigate("changeAddress") },
                        style = TextStyle(color = Color(0xFFFA4A0C), fontSize = 16.sp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text(
                            text = "Universidad ICESI",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(8.dp)
                        )
                        HorizontalDivider(
                            color = Color.Gray,
                            thickness = 1.dp,
                            modifier = Modifier.width(200.dp)
                        )
                        Text(
                            text = addressDetails.location,
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Piso: ${addressDetails.floor}",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.padding(8.dp)
                        )
                        Text(
                            text = "Salón: ${addressDetails.room}",
                            fontWeight = FontWeight.Bold,
                            style = TextStyle(fontSize = 20.sp),
                            modifier = Modifier.padding(8.dp)
                        )

                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Método de entrega",
                    style = TextStyle(fontSize = 20.sp),
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == "Entrega a domicilio",
                                onClick = { viewModel.updateDeliveryMethod("Entrega a domicilio") },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFA4A0C))
                            )
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = selectedOption == "Recogerlo en la tienda",
                                onClick = { viewModel.updateDeliveryMethod("Recogerlo en la tienda") },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFA4A0C))
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Recogerlo en la tienda",
                                style = TextStyle(fontSize = 18.sp),
                                color = Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total", style = TextStyle(fontSize = 20.sp))
                    Text(text = "$productPrice", style = TextStyle(fontSize = 20.sp))
                }

                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = { navController.navigate("resumen") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFA4A0C),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                ) {
                    Text("Proceder al pago", style = TextStyle(fontSize = 20.sp))
                }
            }
        }
    )
}