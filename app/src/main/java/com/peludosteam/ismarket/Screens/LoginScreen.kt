    package com.peludosteam.ismarket.Screens

    import androidx.compose.foundation.background
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.CircularProgressIndicator
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Scaffold
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.material3.TextField
    import androidx.compose.material3.TextFieldDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.livedata.observeAsState
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.input.PasswordVisualTransformation
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.lifecycle.viewmodel.compose.viewModel
    import com.peludosteam.ismarket.viewmode.SignupViewModel
    import androidx.compose.runtime.LaunchedEffect
    import androidx.navigation.NavHostController


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun LoginScreen(navController: NavHostController, authViewModel: SignupViewModel = viewModel()) {
        val authState by authViewModel.authState.observeAsState()
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Recuperar el mensaje de error del ViewModel
        val errorMessage by authViewModel.errorMessage.observeAsState("")

        // Verificar si hay un usuario autenticado al cargar la pantalla
        LaunchedEffect(Unit) {
            if (authViewModel.isUserLoggedIn()) {
                navController.navigate("profile") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color(0xFFF2F2F2)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Inicia sesión",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF1C0000)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Campo de correo electrónico
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )

                // Campo de contraseña
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    colors = TextFieldDefaults.textFieldColors(containerColor = Color.White)
                )

                // Mostrar mensaje de error debajo de los campos
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp) // Añadimos espacio entre los campos y el mensaje
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Estado de autenticación: cargando o exitoso
                when (authState) {
                    1 -> CircularProgressIndicator()
                    3 -> {
                        navController.navigate("profile") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    else -> Unit
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón de iniciar sesión
                Button(
                    onClick = {
                        // Llamamos al método signin sin pasarle un parámetro
                        authViewModel.signin(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFA4A0C),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Iniciar sesión", fontSize = 18.sp)
                }


                Spacer(modifier = Modifier.height(8.dp))

                // Enlace para registrarse
                TextButton(onClick = { navController.navigate("signup") }) {
                    Text(
                        text = "¿No tienes cuenta? Regístrate aquí",
                        color = Color(0xFFFA4A0C)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
