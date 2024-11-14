package com.peludosteam.ismarket.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.viewmode.ProfileViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userState by profileViewModel.user.observeAsState()
    Log.e(">>>", userState.toString())
    val username by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        profileViewModel.getCurrentUser()
    }
    if (userState == null) {
        navController.navigate("login")
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bienvenido ${userState?.name}")
            Button(onClick = {
                Firebase.auth.signOut()
                navController.navigate("login")
            }) {
                Text(text = "Cerrar sesión")
            }

            // Botón para navegar a la pantalla de agregar productos
            Button(onClick = { navController.navigate("addProduct") }) {
                Text(text = "Agregar productos")
            }
            Button(onClick = {
                navController.navigate("viewProducts")
            }) {
                Text(text = "Ver productos disponibles")
            }

        }
    }
}
