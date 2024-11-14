package com.peludosteam.ismarket.Screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

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
        }, icon = { Icon(Icons.Filled.Home, contentDescription = "viewProducts") }, label = { Text(text = "viewProducts") })

        NavigationBarItem(selected = navigationSelectedItemIndex == 1, onClick = {
            nestedNavController.navigate("history"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 1
        }, icon = { Icon(Icons.Filled.List, contentDescription = "history") }, label = { Text(text = "history") })

        NavigationBarItem(selected = navigationSelectedItemIndex == 2, onClick = {
            nestedNavController.navigate("chat"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 2
        }, icon = { Icon(Icons.Filled.Send, contentDescription = "chat") }, label = { Text(text = "chat") })
        NavigationBarItem(selected = navigationSelectedItemIndex == 2, onClick = {
            nestedNavController.navigate("profile"){
                launchSingleTop = true
            }
            navigationSelectedItemIndex = 2
        }, icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "profile") }, label = { Text(text = "profile") })


    }
}