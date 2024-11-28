package com.peludosteam.ismarket.Screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.viewmode.ProfileViewModel

@Composable
fun Chats(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val userState by profileViewModel.user.observeAsState()

    LaunchedEffect(true) {
        profileViewModel.getCurrentUser()
        profileViewModel.getUserList()
    }

    if (userState != null) {
        val userListState by profileViewModel.userList.observeAsState()
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Bienvenido ${userState?.name}")
                    Box(modifier = Modifier.weight(1f))
                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    userListState?.let {
                        items(it) { user ->
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ), modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                onClick = {
                                    user?.let { navController.navigate("chat/${it.id}") }
                                }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(text = user?.name ?: "NO_NAME", color = Color.Blue, fontSize = 24.sp)
                                    Text(text = user?.email ?: "NO_NAME")
                                }

                            }
                        }
                    }


                }

            }
        }

    } else {
        navController.navigate("login")
    }
}