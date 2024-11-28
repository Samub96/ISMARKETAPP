package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peludosteam.ismarket.viewmode.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Cargar datos del usuario
    val userState by profileViewModel.user.observeAsState()

    LaunchedEffect(userState) {
        userState?.let {
            name = it.name
            username = it.username
            email = it.email
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text("Editar Perfil")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF2F2F2)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Nombre de usuario") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Nueva Contraseña (opcional)") },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            )
            Button(
                onClick = {
                    if (password.isNotEmpty()) {
                        profileViewModel.updatePassword(password)
                    }
                    profileViewModel.updateUserProfile(name, username, email, password.ifEmpty { null })
                    navController.navigate("viewProfile")
                },
                modifier = Modifier.fillMaxWidth().padding(16.dp)
            ) {
                Text("Guardar Cambios")
            }
        }
    }
}
