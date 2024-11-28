package com.peludosteam.ismarket.Screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.navigation.NavController
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.domain.Purchase

@Composable
fun PurchaseHistoryScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val userId = auth.currentUser?.uid ?: ""

    var purchases by remember { mutableStateOf<List<Purchase>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        db.collection("purchases")
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val purchaseList = snapshot.documents.mapNotNull { document ->
                    val products = document.get("products") as? List<Map<String, Any>> ?: emptyList()
                    val mappedProducts: List<Product> = products.mapNotNull { product ->
                        try {
                            Product(
                                id = product["id"] as? String ?: "",
                                name = product["name"] as? String ?: "",
                                price = (product["price"] as? Number)?.toInt() ?: 0,
                                stock = (product["quantity"] as? Number)?.toInt() ?: 0
                            )
                        } catch (e: Exception) {
                            null // Ignorar productos mal formateados
                        }
                    }

                    Purchase(
                        id = document.id,
                        userId = document.getString("userId") ?: "",
                        paymentMethodName = document.getString("paymentMethodName") ?: "",
                        deliveryMethodName = document.getString("deliveryMethodName") ?: "",
                        totalAmount = (document.getDouble("totalAmount") ?: 0.0),
                        products = mappedProducts,
                        timestamp = document.getLong("timestamp") ?: 0L
                    )
                }
                purchases = purchaseList
                isLoading = false
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color(0xFFF2F2F2)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Historial de Compras",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1C0000),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (purchases.isEmpty()) {
                Text(
                    text = "No tienes compras registradas.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(purchases) { purchase ->
                        PurchaseItem(purchase = purchase)
                    }
                }
            }
        }
    }
}

@Composable
fun PurchaseItem(purchase: Purchase) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Método de Pago: ${purchase.paymentMethodName}",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Método de Entrega: ${purchase.deliveryMethodName}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Total: $${purchase.totalAmount}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Productos:", fontWeight = FontWeight.Bold)
        purchase.products.forEach { product ->
            Text(
                text = "- ${product.name} (x${product.stock}) - $${product.price}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

