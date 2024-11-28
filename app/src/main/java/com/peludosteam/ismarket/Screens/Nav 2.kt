package com.peludosteam.ismarket.Screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun Nav(navController: NavController) {

    Button(onClick = {
        navController.navigate("login")
    }) {
        Text(text = "Profile")
    }
}