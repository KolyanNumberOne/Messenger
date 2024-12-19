package com.example.homeproject.ui.screens.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.homeproject.data.websocket.WebSocketClient
import com.example.homeproject.data.datamodels.Message
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.data.repositories.MessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

class MessageViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val webSocketClient: WebSocketClient,
    private var idDialog: Int
) : ViewModel() {
    private val _listMessage = MutableStateFlow<List<Message>>(emptyList())
    val listMessage: StateFlow<List<Message>> = _listMessage

    private var isConnected = false


    init {
        viewModelScope.launch {_listMessage.value = messageRepository.getMessage(idDialog) }
        if (!isConnected) {
            setUpDialog(idDialog)

        }
    }

    fun setUpDialog(newIdDialog: Int) {
        if (isConnected) return
        isConnected = true
        webSocketClient.setListener { dialogId, messageJson ->
            if (dialogId == newIdDialog) {
                val message = Json.decodeFromString<Message>(messageJson)
                _listMessage.update { it + message }
            }
        }
        viewModelScope.launch {
            // Если требуется загрузка сообщений из репозитория
            /*messageRepository.getMessage(idDialog).collect { messages ->
                _listMessage.value = messages
            }*/
        }
    }

    fun addMessage(message: String) {

        val newMessage = Message(idDialog, token.id, message, "")

        // Отправка сообщения через WebSocket
        webSocketClient.sendMessage(idDialog, newMessage)


    }

    override fun onCleared() {
        super.onCleared()
        webSocketClient.closeConnection(idDialog)
        isConnected = false
        Log.d("webSocket", "Отключение от диалога")
    }

}

class MessageViewModelFactory @Inject constructor(
    private val messageRepository: MessageRepository,
    private val webSocketClient: WebSocketClient
) {
    fun create(dialogId: Int): MessageViewModel {
        return MessageViewModel(messageRepository,webSocketClient, dialogId)
    }
}


