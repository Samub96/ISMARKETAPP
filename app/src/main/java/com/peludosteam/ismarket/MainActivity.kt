package com.peludosteam.ismarket


import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
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

@OptIn(ExperimentalMaterial3Api::class)
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

            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
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
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        translationX = 4f
                        translationY = 4f
                    }
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        cursorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFF1C0000),
                        unfocusedLabelColor = Color(0xFF1C0000),
                        containerColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                )
            }
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

            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        translationX = 4f
                        translationY = 4f
                    }
            ) {
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nombre de usuario") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        cursorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFF1C0000),
                        unfocusedLabelColor = Color(0xFF1C0000),
                        containerColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                )
            }
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
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        translationX = 4f
                        translationY = 4f
                    }
            ) {
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        cursorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFF1C0000),
                        unfocusedLabelColor = Color(0xFF1C0000),
                        containerColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp),
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
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .graphicsLayer {
                        translationX = 4f
                        translationY = 4f
                    }
            ) {
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        cursorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFF1C0000),
                        unfocusedLabelColor = Color(0xFF1C0000),
                        containerColor = Color.White,
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal,
                    ),
                    modifier = Modifier
                        .padding(4.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(8.dp))
                )
            }

            if (authState == 1) {
                CircularProgressIndicator()
            } else if (authState == 2) {
                Text("Hubo un error", color = Color.Red)
            } else if (authState == 3) {
                navController.navigate("profile")
            }
            Button(
                onClick = {
                    signupViewModel.signup(User(name = name, username = username, email = email), password)
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFA4A0C),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.padding(8.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 38.dp)
                    .height(55.dp)
                    .shadow(4.dp, shape = RoundedCornerShape(12.dp))

                ) {
                Text(text = "Registrarse",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Texto con navegación hacia la pantalla de login
            ClickableText(
                text = AnnotatedString("¿Ya tienes cuenta? Inicia sesión aquí"),
                style = TextStyle(
                    color = Color(0xFFFA4A0C),
                    fontSize = 16.sp,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = {
                    navController.navigate("login")
                }
            )

            if (authState == 1) {
                CircularProgressIndicator()
            } else if (authState == 2) {
                Text("Hubo un error", color = Color.Red)
            } else if (authState == 3) {
                navController.navigate("profile")
            }
        }
    }
}