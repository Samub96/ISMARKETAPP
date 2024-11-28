package com.peludosteam.ismarket.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.peludosteam.ismarket.domain.Product
import com.peludosteam.ismarket.viewmode.CarritoViewMode
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.peludosteam.ismarket.R

@Composable
fun CartProductCard(
    product: Product,
    cartViewModel: CarritoViewMode = viewModel()
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del producto con ajustes para asegurar la carga
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageRes)
                    .crossfade(true)
                    .error(R.drawable.ic_launcher_foreground)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .listener(
                        onError = { _, result -> Log.e("CartProductCard", "Error: ${result.throwable}") },
                        onSuccess = { _, _ -> Log.d("CartProductCard", "Imagen cargada correctamente.") }
                    )
                    .build(),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray)

            )
            Log.d("ProductDetails", "ID: ${product.id}, " +
                    "Name: ${product.name}, Price: ${product.price}," +
                    " Image URL: ${product.imageRes}, " + "description: ${product.description}")

            Spacer(modifier = Modifier.width(4.dp))

            // Información del producto
            Column(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(horizontal = 4.dp)
            ) {

                Text(
                    text = product.name.uppercase(),
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.description,
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Thin,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            // Precio y botón de eliminar
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "$ ${product.price}",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFA4A0A)
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        cartViewModel.removeProductFromCart(product.id)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier.size(width = 100.dp, height = 36.dp)
                ) {
                    Text(text = "Eliminar", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}
