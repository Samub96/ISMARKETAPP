package com.peludosteam.ismarket.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.listasapp.components.ProductCard
import com.peludosteam.ismarket.R
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.ProfileViewModel
import com.peludosteam.ismarket.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewProducts(
    navController: NavController = rememberNavController(),
    productViewModel: ProductViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val productListState by productViewModel.productList.observeAsState()
    val userState by profileViewModel.user.observeAsState()

    // Estado para el texto de búsqueda
    var searchQuery by remember { mutableStateOf("") }

    // Filtrar productos en base al texto de búsqueda
    val filteredProducts = productListState?.filter { product ->
        product.name.contains(searchQuery, ignoreCase = true) ||
                product.categoryName.contains(searchQuery, ignoreCase = true) ||
                product.description.contains(searchQuery, ignoreCase = true)

    } ?: emptyList()

    // Acción se ejecuta al inicio
    LaunchedEffect(true) {
        productViewModel.downloadData()
        //profileViewModel.getCurrentUser()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
            title = {
                    SearchBar(searchQuery) { query -> searchQuery = query }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
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
                categories.forEach { category ->
                    Filtro(innerPadding, filteredProducts, category)
                    //Text(text = "hola ${userState?.name}")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(searchQuery: String, onSearchChange: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color.White, shape = RoundedCornerShape(32.dp)) // Fondo blanco y bordes redondeados
    ) {
        androidx.compose.material3.TextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Buscar productos...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            singleLine = true,
            colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent, // Fondo transparente en Material3
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black,
            )
        )
    }
}

@Composable
fun Filtro(innerPadding: PaddingValues, productListState: List<Product>, catName: String) {
    Text(text = catName)
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(productListState) { product ->
            if (product.categoryName == catName) {
                ProductCard(product = product)
            }
        }
    }
}
