package com.peludosteam.ismarket

import android.net.Uri
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import com.peludosteam.ismarket.domain.User
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


import com.peludosteam.ismarket.ui.theme.ISMARKETTheme
import com.peludosteam.ismarket.viewmode.ProfileViewModel
import com.peludosteam.ismarket.viewmode.SignupViewModel


import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmodel.ProductViewModel
import java.util.UUID


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
        composable("addProduct") { AddProductScreen(navController) }

    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, authViewModel: SignupViewModel = viewModel()) {
    val authState by authViewModel.authState.observeAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                color = Color(0xFF1C0000),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = "Inicia sesión para seguir comprando",
                color = Color(0xFF1C0000),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFFFA4A0C),
                    unfocusedIndicatorColor = Color.Gray
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Field
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color(0xFFFA4A0C),
                    unfocusedIndicatorColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Estado de autenticación
            when (authState) {
                1 -> CircularProgressIndicator()
                2 -> Text(text = "Hubo un error, que no podemos ver todavía", color = Color.Red)
                3 -> navController.navigate("profile")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de iniciar sesión
            Button(
                onClick = { authViewModel.signin(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFA4A0C),
                    contentColor = Color.White
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Iniciar sesión", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Texto para navegar a la pantalla de registro
            ClickableText(
                text = AnnotatedString("¿No tienes cuenta? Regístrate aquí"),
                style = TextStyle(
                    color = Color(0xFFFA4A0C),
                    textDecoration = TextDecoration.Underline
                ),
                onClick = { navController.navigate("signup") }
            )
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val userState by profileViewModel.user.observeAsState()
    // ...

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
                modifier = Modifier
                    .padding(8.dp)
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) } // Para almacenar la URI de la imagen seleccionada
    var isLoading by remember { mutableStateOf(false) } // Estado de carga
    var expanded by remember { mutableStateOf(false) } // Controla la expansión del dropdown
    var category by remember { mutableStateOf("") }
    val categories = listOf(
        "Postres",
        "Comida y bebidas",
        "Tecnologia (Accesorios tecnologicos., cargadores, forros celular, etc)",
        "Tutorias",
        "Servicios (Limpieza, arreglos y carpinteria, plataformas de streaming, etc)",
        "Ropa",
        "Accesorios (Joyeria, pulseras, etc)",
        "Otros"
    )

    val auth = FirebaseAuth.getInstance() // Firebase Auth para obtener el ID del usuario
    val db = FirebaseFirestore.getInstance() // Firestore
    val storage = FirebaseStorage.getInstance() // Firebase Storage para imágenes

    // Lanzador para seleccionar imagen
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF2F2F2)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Agregar Producto",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1C0000),
                fontSize = 24.sp
            )

            // Campo de Nombre
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )

            // Campo de Descripción
            TextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )

            // Dropdown de Categoría
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoría") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { selectedCategory ->
                        DropdownMenuItem(
                            text = { Text(selectedCategory) },
                            onClick = {
                                category = selectedCategory
                                expanded = false
                            }
                        )
                    }
                }
            }

            // Campo de Precio
            TextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )

            // Campo de Stock
            TextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )

            // Botón para seleccionar imagen
            Button(onClick = { imagePickerLauncher.launch("image/*") }) {
                Text(text = "Seleccionar Imagen")
            }

            // Mostrar la imagen seleccionada si existe
            imageUri?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                )
            }

            // Botón para agregar el producto
            Button(
                onClick = {
                    if (name.isNotEmpty() && price.isNotEmpty() && stock.isNotEmpty() && description.isNotEmpty() && imageUri != null && category.isNotEmpty()) {
                        isLoading = true
                        val productId = UUID.randomUUID().toString()

                        // Subir la imagen a Firebase Storage
                        val storageRef = storage.reference.child("product_images/$productId")
                        imageUri?.let { uri ->
                            storageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
                                // Obtener la URL de descarga de la imagen
                                storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                                    // Guardar el producto en Firestore
                                    val userId = auth.currentUser?.uid ?: ""
                                    val newProduct = Product(
                                        id = productId,
                                        name = name,
                                        price = price.toDouble(),
                                        description = description,
                                        imageRes = downloadUri.toString(), // Guardar la URL de la imagen
                                        stock = stock.toInt(),
                                        userId = userId,
                                        categoryName = category
                                    )

                                    // Subir a Firestore
                                    db.collection("products").document(productId)
                                        .set(newProduct)
                                        .addOnSuccessListener {
                                            isLoading = false
                                            navController.popBackStack() // Navega de regreso
                                        }
                                        .addOnFailureListener {
                                            isLoading = false
                                            // Manejar error
                                        }
                                }
                            }
                        }
                    }
                },
                enabled = !isLoading && name.isNotEmpty() && price.isNotEmpty() && stock.isNotEmpty() && description.isNotEmpty() && imageUri != null && category.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA4A0C))
            ) {
                Text(text = if (isLoading) "Cargando..." else "Agregar Producto", color = Color.White)
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}








