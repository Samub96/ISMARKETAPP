package com.peludosteam.ismarket.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MenuScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()  // Ocupa toda la pantalla
            .background(Color(0xFFFA4A0C)) // Fondo naranja
            .padding(16.dp), // Espacio alrededor de la columna
        horizontalAlignment = Alignment.Start, // Alineación de los botones a la izquierda
        verticalArrangement = Arrangement.spacedBy(24.dp) // Aumentar el espaciado entre los botones
    ) {
        // Flecha de retroceso (con espaciado pequeño hacia abajo)
        IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(bottom = 8.dp)) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }

        // Botón de Favoritos
        /*MenuButtonCustom(
            text = "Tus Favoritos",
            icon = Icons.Filled.Favorite,
            onClick = { navController.navigate("favorites") }
        )*/

        // Botón de Órdenes
        MenuButtonCustom(
            text = "Órdenes",
            icon = Icons.Filled.List,
            onClick = { navController.navigate("orderError") }
        )

        // Botón de Ofertas
        MenuButtonCustom(
            text = "Ofertas y promociones",
            icon = Icons.Filled.LocalOffer,
            onClick = { navController.navigate("offertError") }
        )

        // Botón de Dirección
        MenuButtonCustom(
            text = "Dirección",
            icon = Icons.Filled.LocationOn,
            onClick = { navController.navigate("changeAddress") }
        )

        // Botón de Configuración
        /*MenuButtonCustom(
            text = "Configuraciones",
            icon = Icons.Filled.Settings,
            onClick = { navController.navigate("settings") }
        )*/

        // Botón de Cerrar sesión
        MenuButtonCustom(
            text = "Cerrar Sesión",
            icon = Icons.AutoMirrored.Filled.ExitToApp,
            onClick = { FirebaseAuth.getInstance().signOut() }
        )
    }
}

@Composable
fun MenuButtonCustom(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()  // El botón ocupa toda la pantalla
            .padding(vertical = 8.dp) // Espacio entre los botones
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color.White,
                modifier = Modifier.size(24.dp) // Tamaño del icono
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium // Estilo del texto
            )
        }
    }
}
