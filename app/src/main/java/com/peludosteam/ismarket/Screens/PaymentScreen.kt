package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peludosteam.ismarket.viewmode.PaymentViewModel
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.ui.graphics.vector.ImageVector
import com.peludosteam.ismarket.viewmode.DeliveryMethod
import com.peludosteam.ismarket.viewmode.PaymentMethod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(navController: NavController, paymentViewModel: PaymentViewModel = viewModel()) {

    val selectedPaymentMethod = paymentViewModel.selectedPaymentMethod
    val selectedDeliveryMethod = paymentViewModel.selectedDeliveryMethod

    Scaffold(

        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Text(text = "Pago", style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold, fontSize = 40.sp))
                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Método de pago", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold,fontSize = 20.sp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 15.dp)
                        .width(315.dp)
                        .padding(vertical = 12.dp)
                        .background(Color(0xFFFFFFFF), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.selectableGroup()) {
                        PaymentOption(
                            text = "Efectivo",
                            icon = Icons.Filled.AttachMoney,
                            selected = selectedPaymentMethod.id == "cash",
                            onClick = {
                                paymentViewModel.setPaymentMethod(PaymentMethod("cash", "Efectivo"))
                            },
                            selectedIconColor = Color(0xFF47EB93)
                        )
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                        PaymentOption(
                            text = "Transferencia",
                            icon = Icons.Filled.AccountBalanceWallet,
                            selected = selectedPaymentMethod.id == "transfer",
                            onClick = {
                                paymentViewModel.setPaymentMethod(PaymentMethod("transfer", "Transferencia"))
                            },
                            selectedIconColor = Color(0xFFEB4796)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Método de entrega", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold,fontSize = 20.sp))

                Box(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .padding(vertical = 15.dp)
                        .width(315.dp)
                        .padding(vertical = 12.dp)
                        .background(Color(0xFFFFFFFF), RoundedCornerShape(10.dp))
                        .padding(12.dp)
                ) {
                    Column(modifier = Modifier.selectableGroup()) {
                        DeliveryOption(
                            text = "Entrega a domicilio",
                            selected = selectedDeliveryMethod.id == "Home Delivery",
                            onClick = {
                                paymentViewModel.setDeliveryMethod(DeliveryMethod("Home Delivery", "Entrega a domicilio"))
                                      },
                            selectedColor = Color(0xFF81D4FA),
                            unselectedColor = Color.Transparent
                        )
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                        DeliveryOption(
                            text = "Recogerlo",
                            selected = selectedDeliveryMethod.id == "Pickup",
                            onClick = { paymentViewModel.setDeliveryMethod(DeliveryMethod("Pickup", "recogerlo"))
                                      },
                            selectedColor = Color(0xFF81D4FA),
                            unselectedColor = Color.Transparent
                        )

                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Total", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 20.sp))
                    Text(text = "$23,000", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold))
                }

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        paymentViewModel.savePaymentToFirebase(
                            onSuccess = {
                                // Acción en caso de éxito, por ejemplo, navegar hacia atrás
                                navController.popBackStack()
                            },
                            onError = { error ->
                                // Manejo de errores, como mostrar un mensaje al usuario
                                println("Error al guardar el pago: ${error}")
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp)
                        .padding(vertical = 12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D00))
                )  {
                    Text(text = "Proceder al pago", style = MaterialTheme.typography.titleMedium.copy(color = Color.White, fontSize = 25.sp))
                }
            }
        }
    )


}



@Composable
fun PaymentOption(text: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit, selectedIconColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .selectable(selected = selected, onClick = onClick)
            .background(if (selected) Color(0xFFE1F5FE) else Color.Transparent, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(icon, contentDescription = null, tint = if (selected) selectedIconColor else Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, style = MaterialTheme.typography.bodyLarge)
    }
}



@Composable
fun DeliveryOption(text: String, selected: Boolean, onClick: () -> Unit, selectedColor: Color, unselectedColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .selectable(selected = selected, onClick = onClick)
            .background(if (selected) selectedColor else unselectedColor, RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = if (selected) Color.Black else Color.Gray // Cambia el color del texto si está seleccionado
        )
    }
}

