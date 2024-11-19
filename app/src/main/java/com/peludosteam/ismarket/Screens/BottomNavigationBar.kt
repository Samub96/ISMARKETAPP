package com.peludosteam.ismarket.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.errorprone.annotations.Modifier

@Composable
fun BottomNavigationBar(nestedNavController: NavController = rememberNavController()){

    var navigationSelectedItemIndex by remember {
        mutableStateOf(0)
    }

    NavigationBar {
        NavigationBarItem(selected = navigationSelectedItemIndex == 0, onClick = {
            nestedNavController.navigate("viewProducts"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 0
        }, icon = { Icon(Icons.Filled.Home, contentDescription = "viewProducts") }, label = { Text(text = "Inicio") })

        NavigationBarItem(selected = navigationSelectedItemIndex == 1, onClick = {
            nestedNavController.navigate("history"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 1
        }, icon = { Icon(Icons.Filled.List, contentDescription = "history") }, label = { Text(text = "Historial") })

        NavigationBarItem(selected = navigationSelectedItemIndex == 2, onClick = {
            nestedNavController.navigate("addProduct"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 2
        }, icon = { Icon(Icons.Filled.Add, contentDescription = "add product") }, label = { Text(text = "Subir producto ") })


        NavigationBarItem(selected = navigationSelectedItemIndex == 3, onClick = {
            nestedNavController.navigate("chat"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 3
        }, icon = { Icon(Icons.Filled.Send, contentDescription = "chat") }, label = { Text(text = "chat") })


        NavigationBarItem(selected = navigationSelectedItemIndex == 4, onClick = {
            nestedNavController.navigate("viewProfile"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 4
        }, icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "profile") }, label = { Text(text = "Perfil" ) })
    }
}