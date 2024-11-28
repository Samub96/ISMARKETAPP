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
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.peludosteam.ismarket.R
import androidx.compose.ui.unit.dp as dp1
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.peludosteam.ismarket.viewmode.AddressViewModel


@Composable
fun ResumenCompraScreen(navController: NavController, viewModel: AddressViewModel) {
    val addressDetails = viewModel.addressDetails
    val productPrice = viewModel.productPrice
    val deliveryMethod = viewModel.deliveryMethod

    // Cargar los datos del ViewModel
    LaunchedEffect(true) {
        viewModel.listenForAddressUpdates()
        viewModel.listenForProductPrice()
    }

    // Diseño de la pantalla
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp1)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp1)
    ) {
        // Título con flecha de retroceso
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Volver",
                    modifier = Modifier.size(24.dp1)
                )
            }
            Text(
                text = "Resumen De Tu Pedido",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }

        // Título del pedido
        Text(
            text = "Pedido",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )

        // Subtítulo
        Text(
            text = "Resumen de tu pedido",
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
            color = Color.Gray
        )

        // Detalle de productos
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp1),
            shape = RoundedCornerShape(8.dp1),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp1),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ismarket),
                        contentDescription = "Empanadas",
                        modifier = Modifier.size(48.dp1)
                    )
                    Spacer(modifier = Modifier.width(8.dp1))
                    Column {
                        Text(text = "Empanadas", fontWeight = FontWeight.Bold)
                        Text(text = "$1,900 x 6", color = Color.Gray)
                    }
                }
            }
        }

        // Dirección
        Text(
            text = "Dirección",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )

        // Card de Dirección
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp1),
            shape = RoundedCornerShape(8.dp1),

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp1),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Edificio: ${addressDetails.location}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp1))
                Text(text = "Piso: ${addressDetails.floor}", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp1))
                Text(text = "Salón: ${addressDetails.room}", fontWeight = FontWeight.Bold)
            }
        }

        // Método de pago
        Text(
            text = "Método de Pago",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.fillMaxWidth()
        )

        // Card de Método de pago
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp1),
            shape = RoundedCornerShape(8.dp1),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp1),
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CreditCard,
                        contentDescription = "Método de pago",
                        modifier = Modifier.size(24.dp1)
                    )
                    Spacer(modifier = Modifier.width(8.dp1))
                    Text(text = "**** 3239")
                }
            }
        }

        // Precio Total
        Text(
            text = "Total: $productPrice",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.End)
        )

        // Botón de cancelar (naranja)
        Button(
            onClick = { navController.popBackStack() }, // Volver a la pantalla anterior
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp1)
                .height(50.dp1),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5722)) // Naranja
        ) {
            Text(text = "Cancelar", color = Color.White)
        }
    }
}