
package com.peludosteam.ismarket.Screens
/*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.peludosteam.ismarket.R
import com.peludosteam.ismarket.viewmode.PedidoViewModel

@Composable
fun SeguimientoPedidoScreen(navController: NavController, viewModel: PedidoViewModel) {
    val orderStatus = viewModel.orderStatus
    val estimatedDeliveryTime = viewModel.estimatedDeliveryTime
    val deliveryLocation = viewModel.deliveryLocation
    val products = viewModel.products
    val customerServiceNumber = "123-456-789" // Número de contacto del servicio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título de la pantalla
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Seguimiento de tu pedido",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        // Estado del pedido
        Text(
            text = "Estado: $orderStatus",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth(),
            color = Color.Gray
        )

        // Ubicación del repartidor (si tienes seguimiento en tiempo real)
        Text(
            text = "Ubicación del repartidor: $deliveryLocation",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            color = Color.Gray
        )

        // Tiempo estimado de entrega
        Text(
            text = "Tiempo estimado de entrega: $estimatedDeliveryTime",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            color = Color.Gray
        )

        // Detalles del pedido
        Text(
            text = "Detalles de tu pedido",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )

        // Lista de productos
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                products.forEach { product ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ismarket),
                            contentDescription = "Producto",
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(text = product.name, fontWeight = FontWeight.Bold)
                            Text(text = "${product.price} x ${product.quantity}", color = Color.Gray)
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // Botón para contactar al servicio
        Button(
            onClick = { /* Llamar al servicio de atención */ },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)) // Naranja
        ) {
            Text(text = "Contactar al repartidor", color = Color.White)
        }

        // Botón para cancelar el pedido o regresar
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(text = "Cancelar pedido", color = Color.White)
        }
    }
}
*/