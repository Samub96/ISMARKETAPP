import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.peludosteam.ismarket.R
import androidx.compose.ui.unit.dp as dp1
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController


@Composable
fun ResumenCompraScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp1),
        verticalArrangement = Arrangement.spacedBy(16.dp1)
    ) {
        // Título
        Text(
            text = "Resumen De Tu Compra",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )

        // Detalle de productos
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp1)
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
                        painter = painterResource(id = R.drawable.ismarket), // Reemplaza con imagen de producto
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
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp1)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp1),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Dirección", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp1))
                Text(text = "Edificio: C")
                Text(text = "Piso: 3")
                Text(text = "Salón: 304C")
            }
        }

        // Método de pago
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp1)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp1),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Método De Pago", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp1))
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
            text = "Total: $11,400",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.align(Alignment.End)
        )

        // Botón de cancelar
        Button(
            onClick = { /* Acción de cancelar */ },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Cancelar")
        }
    }
}
