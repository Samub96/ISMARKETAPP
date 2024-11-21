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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
                .height(300.dp)
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
            .height(150.dp))

    }
}

@Composable
fun ProdCardBody(product: Product, productViewModel: ProductViewModel){
    val appColor  = Color(0xFFFA4A0A)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(4.dp)) {
        Text(text = "$ ${product.price}", fontSize = 14.sp, color = appColor)
        Text(
            text = product.name.uppercase(),
            fontSize = 16.sp,
            modifier = Modifier.padding(2.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style=TextStyle(fontWeight = FontWeight.Bold),
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
        // Asegura que los botones estén alineados a la misma altura
        var stock by remember {
            mutableStateOf(product.stock)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically, // Alineación vertical
            horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los botones
        ) {
            Button(//Aumentar stock
                onClick = {
                    val newStock = product.stock + 1
                    productViewModel.updateProductStock(product.id, newStock) // Llama al método del ViewModel
                    product.stock = newStock // Actualiza el stock localmente
                    stock=newStock

                          },
                colors = ButtonDefaults.buttonColors(containerColor = appColor),
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = "aumentar stock",
                    modifier = Modifier.size(16.dp),
                    tint = Color.White
                )
            }

            Text(text = "Stock: $stock", fontSize = 12.sp, color = appColor)

            Button(//restar stock
                onClick = {
                    if (product.stock > 0) {
                        val newStock = product.stock - 1
                        productViewModel.updateProductStock(product.id, newStock) // Llama al método del ViewModel
                        product.stock = newStock // Actualiza el stock localmente
                        stock=newStock
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = appColor),
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "disminuir stock",
                    modifier = Modifier.size(12.dp),
                    tint = Color.White
                )
            }
        }

    }
}
