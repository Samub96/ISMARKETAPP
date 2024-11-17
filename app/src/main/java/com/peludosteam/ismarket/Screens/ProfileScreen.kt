package com.peludosteam.ismarket.Screens

import android.util.Log
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listasapp.components.ProductCard
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.AddProductScreen
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.ProfileViewModel
import com.peludosteam.ismarket.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val userState by profileViewModel.user.observeAsState()
    Log.e(">>>", userState.toString())
    val nestedNavController = rememberNavController()

    LaunchedEffect(true) {
        profileViewModel.getCurrentUser()
    }
    if (userState == null) {
        navController.navigate("login")
    } else {
        Scaffold (modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Is Market", color = Color(0xFFFA4A0A)) // Puedes ajustar el título si lo necesitas
                    },
                    navigationIcon = {
                        // Ícono en la izquierda (Menú)
                        IconButton(onClick = { navController.navigate("menu") }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color(0xFFFA4A0A))
                        }
                    },
                    actions = {
                        // Ícono en la derecha (Carrito)
                        IconButton(onClick = { navController.navigate("cart") }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart", tint = Color(0xFFFA4A0A))
                        }
                    },
                )

            },
            bottomBar = { BottomNavigationBar(nestedNavController)}
            ){ innerPadding ->
            NavHost(navController = nestedNavController, startDestination = "viewProducts", modifier = Modifier.padding(innerPadding)) {
                composable("viewProducts"){ ViewProducts()}
                //composable("history"){ ViewProducts()}
                //composable("chat"){ ViewProducts()}
                composable("viewProfile"){ ViewProfile() }
                composable("addProduct") { AddProductScreen(nestedNavController) }
            }
        }
    }
}
