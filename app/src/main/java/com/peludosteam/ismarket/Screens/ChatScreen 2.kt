package com.peludosteam.ismarket.Screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.peludosteam.ismarket.viewmode.ChatViewModel
import com.peludosteam.ismarket.viewmode.ProfileViewModel

@Composable
fun ChatScreen(
    navController: NavController,
    userId: String,
    chatViewModel: ChatViewModel = viewModel(),
    profileViewModel: ProfileViewModel= viewModel()
) {
    var otherUserID by remember { mutableStateOf(userId) }
    val messagesState by chatViewModel.messagesState.observeAsState(listOf())
    var imagesLoaded by remember { mutableStateOf(0) }

    val lazyColumnState = rememberLazyListState()


    LaunchedEffect(true) {
        chatViewModel.getMessagesLiveMode(otherUserID)
    }

    LaunchedEffect(messagesState, imagesLoaded) {
        if(!messagesState.isEmpty()) {
            val itemCount = messagesState.lastIndex
            lazyColumnState.animateScrollToItem(itemCount)
        }
    }


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {

            LazyColumn(
                state = lazyColumnState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                profileViewModel.userList
                items(messagesState) { message ->

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if(message!!.isMine) MaterialTheme.colorScheme.primaryContainer else Color.LightGray,
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {

                        message?.imageURL?.let {
                            AsyncImage(
                                model = it,
                                contentDescription = "",
                                modifier = Modifier.fillMaxWidth(),
                                onSuccess = {
                                    imagesLoaded++
                                }
                            )
                        }

                        Text(
                            text = message?.content ?: "NO_MESSAGE",
                            modifier = Modifier
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                        )
                    }

                }
            }

            MessageComposer(otherUserID)


        }
    }
}

@Composable
fun MessageComposer(
    otherUserID: String,
    chatViewModel: ChatViewModel = viewModel()
) {
    var messageText by remember { mutableStateOf("") }
    var selectedUri: Uri? by remember { mutableStateOf(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->  //image://234234234 -> /storage/Emulated/0/Andorid/hola.png
        selectedUri = uri
    }

    Column {
        selectedUri?.let {
            AsyncImage(
                model = it, contentDescription = "",
                modifier = Modifier.size(100.dp)
            )
        }
        Row {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f)
            )

            Button(onClick = {
                launcher.launch("image/*")
            }) {
                Icon(imageVector = Icons.Rounded.AddCircle, contentDescription = "")
            }

            Button(onClick = {
                chatViewModel.sendMessage(messageText, selectedUri, otherUserID)
                selectedUri = null
                messageText = ""
            }) {
                Text(text = "Enviar")
            }
        }
    }

}