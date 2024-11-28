package com.peludosteam.ismarket.viewmode

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peludosteam.ismarket.domain.Message
import com.peludosteam.ismarket.repository.ChatRepository
import com.peludosteam.ismarket.repository.ChatRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ChatViewModel(
    val chatRepository: ChatRepository = ChatRepositoryImpl()
) : ViewModel() {

    private val _messagesState = MutableLiveData<List<Message?>>()
    val messagesState: LiveData<List<Message?>> get() = _messagesState

    fun getMessages(otherUserID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val messages = chatRepository.getMessages(otherUserID)
            withContext(Dispatchers.Main) { _messagesState.value = messages }
        }
    }

    fun sendMessage(content: String, uri:Uri?, otherUserID: String) {
        val message = Message(
            UUID.randomUUID().toString(),
            content
        )
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.sendMessage(message, uri, otherUserID)
        }
    }

    fun getMessagesLiveMode(otherUserID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chatRepository.getLiveMessages(otherUserID) { message ->
                val currentMessages = _messagesState.value ?: arrayListOf()
                val updatedMessages = ArrayList(currentMessages)
                updatedMessages.add(message)
                withContext(Dispatchers.Main){ _messagesState.value = updatedMessages}
            }
        }

    }

}