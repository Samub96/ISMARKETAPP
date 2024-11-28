package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listasapp.components.ProductCardEditView
import com.peludosteam.ismarket.R
import com.peludosteam.ismarket.viewmode.ProductViewModel
import com.peludosteam.ismarket.viewmode.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ViewProfile(
    navController: NavController, // Elimina el valor predeterminado
    productViewModel: ProductViewModel = viewModel(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    val productListState by productViewModel.productList.observeAsState()
    val userState by profileViewModel.user.observeAsState()

    // Carga de datos al inicio
    LaunchedEffect(true) {
        productViewModel.downloadData()
        profileViewModel.getCurrentUser()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Encabezado con imagen y avatar
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.ismarket), // Imagen de fondo
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pp), // Avatar del usuario
                        contentDescription = "User Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Text(
                        text = userState?.name ?: "Usuario",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Text(
                        text = "Descripcion",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            //Botones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                /*Button(onClick = { /* Acción seguir */ }) {
                    Text(text = "Siguiendo")
                }*/
                Button(onClick = { navController.navigate("editProfile") }) { // Cambiar para usar "editProfile"
                    Text(text = "Editar perfil")
                }
                /*Button(onClick = { /* Acción compartir */ }) {
                    Text(text = "Compartir")
                }*/
            }

            // Lista de productos en cuadrícula
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 columnas
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 4.dp),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                productListState?.filter { it.userId == userState?.id }?.let { filteredProducts ->
                    items(filteredProducts) { product ->
                        ProductCardEditView(product = product, productViewModel = productViewModel)
                    }
                }
            }
        }
    }
}


//fun ViewProfile(
//    navController: NavController = rememberNavController(),
//    productViewModel: ProductViewModel = viewModel(),
//    profileViewModel: ProfileViewModel = viewModel()
//) {
//    val productListState by productViewModel.productList.observeAsState()
//    val userState by profileViewModel.user.observeAsState()
//
//
//    // Acción se ejecuta al inicio
//    LaunchedEffect(true) {
//        productViewModel.downloadData()
//        profileViewModel.getCurrentUser()
//
//    }
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        topBar = {
//            TopAppBar(
//                title = {     Text(text = "hola ${userState?.name}")
//                },
////                navigationIcon = {
////                    IconButton(onClick = { navController.popBackStack() }) {
////                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
////                    }
////                }
//            )
//        },
//        //bottomBar = { BottomNavigationBar(navController) }
//    ) { innerPadding ->
//
//
//        LazyVerticalGrid(
//
//            columns = GridCells.Fixed(2), // 2 columnas para cada fila
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(4.dp), // Espacio entre los elementos
//            contentPadding = PaddingValues(8.dp), // Margen general
//            horizontalArrangement = Arrangement.spacedBy(2.dp),
//            verticalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            // Filtra y muestra solo los productos del usuario específico
//            productListState?.filter { product -> product.userId == userState?.id }?.let { filteredProducts ->
//                items(filteredProducts) { product ->
//                    ProductCardEditView(product = product)
//                }
//            }
//        }
//    }
//}
