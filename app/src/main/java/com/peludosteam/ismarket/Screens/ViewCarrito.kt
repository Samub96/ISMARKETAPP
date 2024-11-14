package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.peludosteam.ismarket.Domain.Product
import com.peludosteam.ismarket.viewmode.CarritoViewMode
import com.example.listasapp.components.ProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCarrito(navController: NavController, cartViewModel: CarritoViewMode = viewModel()) {
    val cartProducts by cartViewModel.cartProducts.observeAsState(emptyList())
    val totalPrice by cartViewModel.totalPrice.observeAsState(0.0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Carrito de Compras") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(8.dp)
        ) {
            if (cartProducts.isEmpty()) {
                // Muestra un mensaje cuando el carrito está vacío
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                ) {
                    items(cartProducts) { product ->
                        ProductCard(product = product)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Muestra el precio total del carrito
                Text(
                    text = "Total: $${"%.2f".format(totalPrice)}",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                // Botón para finalizar la compra
                Button(
                    onClick = { cartViewModel.checkout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Finalizar Compra")
                }
            }
        }
    }
}
