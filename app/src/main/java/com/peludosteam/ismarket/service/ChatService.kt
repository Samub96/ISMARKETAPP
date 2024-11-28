package com.peludosteam.ismarket.service

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.peludosteam.ismarket.domain.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

interface ChatService {
    suspend fun searchChatId(userID1: String, userID2: String): String
    suspend fun sendMessage(message: Message, chatroomID: String)
    suspend fun getMessages(chatroomID: String): List<Message?>
    fun getLiveMessages(chatroomID: String, callback: suspend (QueryDocumentSnapshot) -> Unit)
    suspend fun uploadImage(uri:Uri, id:String)
    suspend fun getImageURLByID(it: String):String
}

class ChatServiceImpl : ChatService {
    override suspend fun searchChatId(userID1: String, userID2: String): String {
        val result = Firebase.firestore.collection("users")
            .document(userID2)
            .collection("chats")
            .whereEqualTo("userid", userID1)
            .get()
            .await()
        if (result.documents.size == 0) {
            val chatID = UUID.randomUUID().toString()
            val relationshipA = hashMapOf(
                "id" to UUID.randomUUID().toString(),
                "userid" to userID1,
                "chatid" to chatID
            )
            val relationshipB = hashMapOf(
                "id" to UUID.randomUUID().toString(),
                "userid" to userID2,
                "chatid" to chatID
            )
            //WriteBatch
            Firebase.firestore.collection("users")
                .document(userID1).collection("chats")
                .document(relationshipB["id"] ?: "").set(relationshipB).await()
            Firebase.firestore.collection("users")
                .document(userID2).collection("chats")
                .document(relationshipA["id"] ?: "").set(relationshipA).await()
            return chatID
        } else {
            val chatid = result.documents[0].get("chatid").toString()
            return chatid
        }
    }

    override suspend fun sendMessage(message: Message, chatroomID: String) {
        Firebase.firestore.collection("chats")
            .document(chatroomID)
            .collection("messages")
            .document(message.id)
            .set(message)
            .await()
    }

    override suspend fun getMessages(chatroomID: String): List<Message?> {
        val result = Firebase.firestore
            .collection("chats")
            .document(chatroomID)
            .collection("messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .get()
            .await()
        val messages = result.documents.map { doc ->
            doc.toObject(Message::class.java)
        }
        return messages
    }

    override fun getLiveMessages(chatroomID: String, callback: suspend (QueryDocumentSnapshot) -> Unit) {
        Firebase.firestore
            .collection("chats")
            .document(chatroomID)
            .collection("messages")
            .orderBy("date", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, err ->
                CoroutineScope(Dispatchers.IO).launch {
                    snapshot?.documentChanges?.forEach { documentChange ->
                        if (documentChange.type == DocumentChange.Type.ADDED) {
                            callback(documentChange.document)
                        }
                    }
                }
            }
    }

    override suspend fun uploadImage(uri: Uri, id: String) {
        Firebase.storage.reference
            .child("chatImages").child(id)
            .putFile(uri).await()
    }

    override suspend fun getImageURLByID(id: String): String {
        val result = Firebase.storage.reference.child("chatImages")
            .child(id)
            .downloadUrl.await()
        return result.toString()
    }
}