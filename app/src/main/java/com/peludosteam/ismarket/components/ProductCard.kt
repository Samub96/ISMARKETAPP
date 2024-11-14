package com.example.listasapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.peludosteam.ismarket.domain.Product

@Composable
fun ProductCard (product: Product){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            //.fillMaxWidth()
            .padding(8.dp).width(300.dp)

    ) {
        Column(
            modifier= Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(4.dp),
            ){
            ProductCardHeader(product)
            ProductCardBody(product)
        }
    }
}

@Composable
fun ProductCardHeader(product: Product){
    Box(modifier = Modifier
        .fillMaxWidth()
        .clip(RoundedCornerShape(16.dp))
        .background(Color.White)){
        AsyncImage(model = product.imageRes, contentDescription = product.name, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(200.dp))

    }
}

@Composable
fun ProductCardBody(product: Product){
    val appColor  = Color(0xFFFA4A0A)
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Text(text = "$ ${product.price}", fontSize = 32.sp, color = appColor)
        Text(
            text = product.name.uppercase(),
            fontSize = 14.sp,
            modifier = Modifier.padding(4.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style=TextStyle(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
        Text(
            text = product.description,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.Gray,
        )

        // Asegura que los botones estén alineados a la misma altura
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically, // Alineación vertical
            horizontalArrangement = Arrangement.SpaceBetween // Espacio entre los botones
        ) {
            Button(//Chatear con el vendedor
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = appColor),
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "chat",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }

            Button(//Añadir al carrito
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = appColor),
            ) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "carrito",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}
