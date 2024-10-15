package com.peludosteam.ismarket

import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Domain.User
import com.peludosteam.ismarket.ui.theme.ISMARKETTheme
import com.peludosteam.ismarket.viewmode.ProfileViewModel
import com.peludosteam.ismarket.viewmode.SignupViewModel

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth = Firebase.auth

        auth.addAuthStateListener { firebaseAuth ->
            val user = firebaseAuth.currentUser
            if (user != null) {
                Log.d("MainActivity", "User is signed in: ${user.uid}")
            } else {
                Log.d("MainActivity", "No user is signed in")
            }
        }


        setContent {
        ISMARKETTheme {
                App()
                }
            }
        }
    }
@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signup") {
        composable("profile") { ProfileScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("login") { LoginScreen(navController) }
    }
}

@Composable
fun LoginScreen(navController: NavController, authViewModel: SignupViewModel = viewModel()) {
    val authState by authViewModel.authState.observeAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        TextField(value = email, onValueChange = { email = it })
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation()
        )
        if (authState == 1) {
            CircularProgressIndicator()
        } else if (authState == 2) {
            Text(text = "Hubo un error, que no podemos ver todavia")
        } else if (authState == 3) {
            navController.navigate("profile")
        }

        Button(onClick = {
            authViewModel.signin(email, password)
        }) {
            Text(text = "Iniciar sesion")
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val userState by profileViewModel.user.observeAsState()
    Log.e(">>>", userState.toString())
    val username by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        profileViewModel.getCurrentUser()
    }
    if (userState == null) {
        navController.navigate("login")
    } else {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(text = "Bienvenido ${userState?.name}")
            Button(onClick = {
                Firebase.auth.signOut()
                navController.navigate("login")
            }) {
                Text(text = "Cerrar sesión")
            }
        }
    }
}

@Composable
fun SignupScreen(navController: NavController, signupViewModel: SignupViewModel = viewModel()) {
    val authState by signupViewModel.authState.observeAsState()

    var name by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var context = LocalContext.current
    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF2F2F2)),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,) {
                Text(
                    text = "Registrarte",
                    color = Color(0xFF1C0000),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                )
                    Text( modifier = Modifier.padding(18.dp),
                        text = "Regístrate para disfrutar todos los beneficios de la aplicación",
                        color = Color(0xFF1C0000),
                        textAlign = TextAlign.Center
                    )
                }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),  // Alineación y margen lateral
                horizontalAlignment = androidx.compose.ui.Alignment.Start
            ) {
                Text(
                    text = "Nombre",
                    color = Color(0xFF1C0000),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            TextField(value = name,
                modifier = Modifier.padding(bottom = 8.dp),
                onValueChange = { name = it },
                label = { Text("Nombre") },
                )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),  // Alineación y margen lateral
                horizontalAlignment = androidx.compose.ui.Alignment.Start
            ) {
                Text(
                    text = "Nombre de Usuario ",
                    color = Color(0xFF1C0000),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }

            TextField(value = username,
                modifier = Modifier.padding(bottom = 8.dp),
                onValueChange = { username = it },
                      label = { Text("Nombre de Usuario") })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),  // Alineación y margen lateral
                horizontalAlignment = androidx.compose.ui.Alignment.Start
            ) {
                Text(
                    text = "Correo",
                    color = Color(0xFF1C0000),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 5.dp)
                )
            }
            TextField(value = email,
                      modifier = Modifier.padding(bottom = 8.dp),
                      onValueChange = { email = it },
                      label = { Text("Correo") })
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),  // Alineación y margen lateral
                horizontalAlignment = androidx.compose.ui.Alignment.Start
            ) {
                Text(
                    text = "Contraseña",
                    color = Color(0xFF1C0000),
                    textAlign = TextAlign.Start,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.padding(bottom = 5.dp),
                    )
            }
            TextField(value = password,
                modifier = Modifier.padding(bottom = 8.dp),
                onValueChange = { password = it },
                      label = { Text("Contraseña") },
                      visualTransformation = PasswordVisualTransformation()

            )

            if (authState == 1) {
                CircularProgressIndicator()
            } else if (authState == 2) {
                Text("Hubo un error", color = Color.Red)
            } else if (authState == 3) {
                navController.navigate("profile")
            }
            Button(onClick = {
                signupViewModel.signup(User(name = name, username = username, email = email), password)
            }) {
                Text(text = "Registrarse")
            }
        }
    }
}