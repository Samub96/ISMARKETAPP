package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController


@Composable
fun Cart(navController: NavController) {

    Column {
        Button(onClick = { navController.navigate("address")}) {
            Text(text = "address")
        }
        Button(onClick = { navController.navigate("orderError")}) {
            Text(text = "orderError")

        }
        Button(onClick = { navController.navigate("changeAddress")}) {
            Text(text = "changeAddress")

        }
        Button(onClick = { navController.navigate("offertError")}) {
            Text(text = "offertError")

        }
        Button(onClick = { navController.navigate("wifiError")}) {
            Text(text = "wifiError")

        }

    }

}