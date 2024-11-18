package com.peludosteam.ismarket

import android.net.Uri
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.Screens.ViewProducts
import com.peludosteam.ismarket.ui.theme.ISMARKETTheme
import com.peludosteam.ismarket.viewmode.ProfileViewModel

import coil.compose.rememberImagePainter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.peludosteam.ismarket.Screens.AddressScreen
import com.peludosteam.ismarket.Screens.ChangeAddressScreen
import com.peludosteam.ismarket.Screens.EnterScreen
import com.peludosteam.ismarket.Screens.LoginScreen
import com.peludosteam.ismarket.Screens.OffertErrorScreen
import com.peludosteam.ismarket.Screens.OrderErrorScreen
import com.peludosteam.ismarket.Screens.ProfileScreen
import com.peludosteam.ismarket.Screens.SignupScreen
import com.peludosteam.ismarket.Screens.WifiErrorScreen
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.AddressViewModel
import com.peludosteam.ismarket.viewmode.SignupViewModel
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
    NavHost(navController = navController, startDestination = "enter") {
        composable("enter") { EnterScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("login") { LoginScreen(navController) }
        composable("addProduct") { AddProductScreen(navController) }
        composable("viewProducts") { ViewProducts(navController) }
        composable("address") {
            val addressViewModel: AddressViewModel = viewModel()
            AddressScreen(navController, addressViewModel)
        }
        composable("orderError") { OrderErrorScreen(navController) }
        composable("changeAddress") { ChangeAddressScreen(navController) }
        composable("offertError") { OffertErrorScreen(navController) }
        composable("wifiError") { WifiErrorScreen(navController) }



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
                                        price = price.toInt(),
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








