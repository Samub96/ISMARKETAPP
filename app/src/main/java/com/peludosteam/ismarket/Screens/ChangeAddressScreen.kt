package com.peludosteam.ismarket.Screens
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.peludosteam.ismarket.R
import kotlinx.coroutines.delay


@Composable
fun CustomToast(message: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(Color(0xFFFA4A0C), shape = RoundedCornerShape(12.dp))
            .padding(vertical = 12.dp, horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeAddressScreen(navController: NavController) {
    var showCustomToast by remember { mutableStateOf(false) }
    var firstInput by remember { mutableStateOf("") }
    var secondInput by remember { mutableStateOf("") }
    var thirdInput by remember { mutableStateOf("") }

    val db = FirebaseFirestore.getInstance()

    fun saveAddressToFirestore(building: String, floor: String, room: String) {
        val address = hashMapOf(
            "building" to building,
            "floor" to floor,
            "room" to room,
            "timestamp" to com.google.firebase.Timestamp.now()
        )
        db.collection("Addresses")
            .add(address)
            .addOnSuccessListener {
                showCustomToast = true
            }
            .addOnFailureListener { e ->
                println("Error al guardar la dirección: ${e.message}")
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.flecha),
                contentDescription = "Flecha",
                modifier = Modifier
                    .size(15.dp)
                    .graphicsLayer { rotationZ = 90f }
                    .clickable {
                        navController.navigate("address") {
                            launchSingleTop = true

                        }
                    }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Ubicación",
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Ubicación",
            style = TextStyle(fontSize = 30.sp),
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Box(
            modifier = Modifier
                .height(900.dp)
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(12.dp))
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.torre),
                    contentDescription = "Imagen de torre",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
                Spacer(modifier = Modifier.height(40.dp))
                TextField(
                    value = firstInput,
                    onValueChange = { firstInput = it },
                    label = { Text("Edificio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        unfocusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = secondInput,
                    onValueChange = { secondInput = it },
                    label = { Text("Piso") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = thirdInput,
                    onValueChange = { thirdInput = it },
                    label = { Text("Salón") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFFFA4A0C), RoundedCornerShape(4.dp)),
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color(0xFFFA4A0C),
                        focusedLabelColor = Color(0xFFFA4A0C),
                        unfocusedLabelColor = Color(0xFFFA4A0C),
                        containerColor = Color.White
                    ),
                    textStyle = TextStyle(
                        color = Color(0xFF1C0000),
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.height(160.dp))
                Button(
                    onClick = {
                        saveAddressToFirestore(firstInput, secondInput, thirdInput)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFA4A0C),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 38.dp)
                        .height(55.dp)
                        .shadow(4.dp, shape = RoundedCornerShape(12.dp))
                ) {
                    Text(
                        text = "Guardar Ubicación",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                }

                AnimatedVisibility(
                    visible = showCustomToast,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    CustomToast(message = "Ubicación guardada exitosamente")
                }

                LaunchedEffect(showCustomToast) {
                    if (showCustomToast) {
                        delay(2000)
                        showCustomToast = false
                        navController.navigate("address")
                    }
                }
            }
        }
    }
}




