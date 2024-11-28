package com.peludosteam.ismarket.Screens

import ResumenCompraScreen
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peludosteam.ismarket.AddProductScreen
import com.peludosteam.ismarket.viewmode.AddressViewModel
import com.peludosteam.ismarket.viewmode.ProfileViewModel
import com.peludosteam.ismarket.viewmode.SignupViewModel
import com.peludosteam.ismarket.Screens.PurchaseHistoryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel(),
    signupViewModel: SignupViewModel = viewModel(),
) {
    val userState by profileViewModel.user.observeAsState()
    Log.e("dataUser", userState.toString())
    val nestedNavController = rememberNavController()

    LaunchedEffect(true) {
        profileViewModel.getCurrentUser()
    }
    if (userState == null) {
        navController.navigate("login")
    } else {
        Scaffold (modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Is Market", color = Color(0xFFFA4A0A))
                    },
                    navigationIcon = {

                        IconButton(onClick = { nestedNavController.navigate("menu") }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = Color(0xFFFA4A0A))
                        }
                    },
                    actions = {

                        IconButton(onClick = { nestedNavController.navigate("ViewCart") }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart", tint = Color(0xFFFA4A0A))
                        }
                    },
                )
            },

            bottomBar = { BottomNavigationBar(nestedNavController)}
        ){ innerPadding ->

            NavHost(navController = nestedNavController, startDestination = "viewProducts", modifier = Modifier.padding(innerPadding)) {
                composable("viewProducts"){ ViewProducts(nestedNavController)}
                //composable("history"){ ViewProducts()}
                //composable("chat"){ ViewProducts()}
                composable("viewProfile") { ViewProfile(navController = nestedNavController) }
                composable("editProfile") { EditProfileScreen(nestedNavController) }
                composable("addProduct") { AddProductScreen(nestedNavController) }
                composable("cart") { Cart(nestedNavController) }
                composable("menu") { MenuScreen( navController) }
                composable("history") { PurchaseHistoryScreen(navController = nestedNavController) }
                composable("address") {
                    val addressViewModel: AddressViewModel = viewModel()
                    AddressScreen(nestedNavController, addressViewModel)
                }

                composable("orderError") { OrderErrorScreen(nestedNavController) }
                composable("changeAddress") { ChangeAddressScreen(nestedNavController) }
                composable("offertError") { OffertErrorScreen(nestedNavController) }
                composable("wifiError") { WifiErrorScreen(nestedNavController) }
                composable("PaymentScreen") { PaymentScreen(nestedNavController) }
                composable("ViewCart") { ViewCarrito(nestedNavController) }

                composable("resumen"){
                    val addressViewModel: AddressViewModel = viewModel()
                    ResumenCompraScreen(nestedNavController, addressViewModel)}

                composable("chats") { Chats(nestedNavController) }

                composable("chat/{userId}") { backEntryStack ->
                    val userId = backEntryStack.arguments?.getString("userId")
                    userId?.let { ChatScreen(nestedNavController, it) }
                }


            }
        }
    }
}


