package com.peludosteam.ismarket.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.peludosteam.ismarket.domain.Message
import com.peludosteam.ismarket.service.ChatService
import com.peludosteam.ismarket.service.ChatServiceImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

interface ChatRepository {
    suspend fun sendMessage(message: Message, uri: Uri?, otherUserId: String)
    suspend fun getMessages(otherUserId: String): List<Message?>
    suspend fun getLiveMessages(otherUserId: String, callback: suspend (Message) -> Unit)
    suspend fun getChatID(otherUserID: String): String
}

class ChatRepositoryImpl(
    val chatService: ChatService = ChatServiceImpl()
) : ChatRepository {

    override suspend fun sendMessage(message: Message, uri: Uri?, otherUserId: String) {
        uri?.let {
            val imageID = UUID.randomUUID().toString()
            message.imageID = imageID
            chatService.uploadImage(it, imageID)
        }
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        message.authorID = Firebase.auth.currentUser?.uid ?: ""
        chatService.sendMessage(message, chatID)
    }

    override suspend fun getMessages(otherUserId: String): List<Message?> {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        val messages = chatService.getMessages(chatID)
        return messages
    }

    override suspend fun getLiveMessages(otherUserId: String, callback: suspend (Message) -> Unit) {
        val chatID = chatService.searchChatId(otherUserId, Firebase.auth.currentUser?.uid ?: "")
        chatService.getLiveMessages(chatID) { doc ->
            val msg = doc.toObject(Message::class.java)
            msg.imageID?.let {
                //Pedir la URL
                val url = chatService.getImageURLByID(it)
                msg.imageURL = url
            }
            msg.isMine = msg.authorID == Firebase.auth.currentUser?.uid
            callback(msg)
        }
    }

    override suspend fun getChatID(otherUserID: String): String {
        return chatService.searchChatId(otherUserID, Firebase.auth.currentUser?.uid ?: "")
    }

}