package com.peludosteam.ismarket.Screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peludosteam.ismarket.viewmode.SignupViewModel

@Composable
fun Cart(navController: NavController,) {

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Button(onClick = { navController.navigate("address") }) {
            Text(text = "address")
        }
        Button(onClick = { navController.navigate("orderError") }) {
            Text(text = "orderError")
        }
        Button(onClick = { navController.navigate("changeAddress") }) {
            Text(text = "changeAddress")
        }
        Button(onClick = { navController.navigate("offertError") }) {
            Text(text = "offertError")
        }
        Button(onClick = { navController.navigate("wifiError") }) {
            Text(text = "wifiError")
        }
        Button(onClick = { navController.navigate("PaymentScreen") }) {
            Text(text = "PaymentScreen")
        }
        Button(onClick = { navController.navigate("nav") }) {
            Text(text = "ProfileScreen")
        }
        Button(onClick = { navController.navigate("ViewCart")}) {
            Text(text = "View Carrito")

        }

    }
}