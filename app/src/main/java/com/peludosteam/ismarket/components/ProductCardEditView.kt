package com.example.listasapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.ProductViewModel

@Composable
fun ProductCardEditView (product: Product, productViewModel: ProductViewModel = viewModel()){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            //.fillMaxWidth()
            .padding(2.dp)
            .width(350.dp)

    ) {
        Column(
            modifier= Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(4.dp),
            ){
            ProdCardHeader(product)
            ProdCardBody(product, productViewModel)
        }
    }
}

@Composable
fun ProdCardHeader(product: Product){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)){
        AsyncImage(model = product.imageRes, contentDescription = product.name, modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(120.dp))

    }
}

@Composable
fun ProdCardBody(product: Product, productViewModel: ProductViewModel) {
    val appColor = Color(0xFFFA4A0A)

    // Estado para mostrar el diálogo de confirmación
    var showDeleteDialog by remember { mutableStateOf(false) }

    // Estado del stock
    var stock by remember { mutableStateOf(product.stock) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Text(text = "$ ${product.price}", fontSize = 14.sp, color = appColor)
        Text(
            text = product.name.uppercase(),
            fontSize = 16.sp,
            modifier = Modifier.padding(2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Text(
            text = product.description,
            fontSize = 10.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto de stock fuera de la Row
            Text(text = "Stock: $stock", fontSize = 15.sp, color = appColor)

            // Fila para los botones de aumentar y disminuir stock
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), // Espacio entre el texto y los botones
                horizontalArrangement = Arrangement.SpaceEvenly, // Distribución uniforme
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sección para aumentar stock
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            val newStock = product.stock + 1
                            productViewModel.updateProductStock(product.id, newStock)
                            product.stock = newStock
                            stock = newStock
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = appColor),
                        modifier = Modifier
                            .height(50.dp) // Asegura una altura fija para el botón
                            .width(80.dp) // Ancho fijo para el botón
                            .padding(2.dp) // Padding para los botones
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Aumentar stock",
                            modifier = Modifier.size(30.dp), // Tamaño ajustado del ícono
                            tint = Color.White
                        )
                    }
                }

                // Sección para disminuir stock
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            if (product.stock > 0) {
                                val newStock = product.stock - 1
                                productViewModel.updateProductStock(product.id, newStock)
                                product.stock = newStock
                                stock = newStock
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = appColor),
                        modifier = Modifier
                            .height(50.dp) // Asegura una altura fija para el botón
                            .width(80.dp) // Ancho fijo para el botón
                            .padding(2.dp) // Padding para los botones
                    ) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Disminuir stock",
                            modifier = Modifier.size(30.dp), // Tamaño ajustado del ícono
                            tint = Color.White
                        )
                    }
                }
            }

            // Fila para el botón de eliminar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp), // Espacio entre las filas
                horizontalArrangement = Arrangement.Center, // Centrar el botón de eliminar
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(
                        onClick = {
                            // Mostrar el diálogo de confirmación
                            showDeleteDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        modifier = Modifier
                            .height(50.dp) // Asegura una altura fija para el botón
                            .width(80.dp) // Ancho fijo para el botón
                            .padding(2.dp) // Padding para los botones
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Delete,
                            contentDescription = "Eliminar producto",
                            modifier = Modifier.size(30.dp), // Tamaño ajustado del ícono
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para eliminar
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                // Cerrar el diálogo sin hacer nada
                showDeleteDialog = false
            },
            title = {
                Text(text = "Confirmación")
            },
            text = {
                Text("¿Estás seguro de que quieres eliminar este producto?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Ejecutar la eliminación del producto
                        productViewModel.deleteProduct(product.id)
                        showDeleteDialog = false
                    }
                ) {
                    Text("Sí", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        // Solo cerrar el diálogo sin hacer nada
                        showDeleteDialog = false
                    }
                ) {
                    Text("No", color = Color.Gray)
                }
            }
        )
    }
}






