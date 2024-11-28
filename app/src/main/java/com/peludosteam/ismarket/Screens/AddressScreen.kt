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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peludosteam.ismarket.R
import com.peludosteam.ismarket.viewmode.AddressViewModel
import com.peludosteam.ismarket.viewmode.CarritoViewMode
import com.peludosteam.ismarket.viewmode.PaymentViewModel

@Composable
fun AddressScreen(navController: NavController, viewModel: AddressViewModel, paymentViewModel: PaymentViewModel = viewModel(),
                  cartViewModel: CarritoViewMode = viewModel(),) {
    val selectedOption = viewModel.deliveryMethod
    val addressDetails = viewModel.addressDetails
    val productPrice = viewModel.productPrice
    val totalPrice by cartViewModel.totalPrice.observeAsState(0)
    val totalAmount =paymentViewModel.setAmount(totalPrice)

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

                Box(
                    modifier = Modifier
                        .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {

                }

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Total", style = TextStyle(fontSize = 20.sp))
                    Text(text = "$totalPrice", style = TextStyle(fontSize = 20.sp))
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
                    Text("finalizar  pago", style = TextStyle(fontSize = 20.sp))
                }
            }
        }
    )
}