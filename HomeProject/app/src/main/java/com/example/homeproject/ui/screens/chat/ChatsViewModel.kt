package com.example.homeproject.ui.screens.chat

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.homeproject.data.websocket.WebSocketClient
import com.example.homeproject.data.datamodels.Dialogues
import com.example.homeproject.data.datamodels.User
import com.example.homeproject.data.datamodels.token
import com.example.homeproject.data.repositories.DialoguesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatsViewModel @Inject constructor(
    application: Application,
    private val dialoguesRepository: DialoguesRepository,
    private val webSocketClient: WebSocketClient
): ViewModel() {

    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val app = application

    private val _listDialogues = MutableStateFlow<List<Dialogues>>(emptyList())
    val listDialogues: StateFlow<List<Dialogues>> = _listDialogues

    private val _listUsers = MutableStateFlow<List<User>>(emptyList())
    val listUsers: StateFlow<List<User>> = _listUsers


    private val _imageBitmap = mutableStateOf<Bitmap?>(null)
    val imageBitmap: MutableState<Bitmap?> = _imageBitmap

    init {
        viewModelScope.launch {
            try {

                _listDialogues.value = dialoguesRepository.getDialogues(token.id)

                _listDialogues.collect { dialogues ->
                    dialogues.forEach { dialogue ->
                        try {
                            connectToDialog(dialogue.id)
                        } catch (e: Exception) {
                            Log.e("DialogConnection", "Ошибка подключения к диалогу ${dialogue.id}: ${e.message}")
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("DialoguesError", "Ошибка получения диалогов: ${e.message}")
            }
        }
    }


    suspend fun connectToDialog(idDialog: Int){
        webSocketClient.connectToDialog(idDialog)
        Log.d("webSocket", "подключение к диалогу $idDialog")
    }


    override fun onCleared() {
        super.onCleared()
        webSocketClient.closeAllConnections()

        Log.d("webSocket", "Отключение от диалога")
        viewModelScope.cancel()
    }
}


