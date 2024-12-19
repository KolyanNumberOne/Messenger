package com.example.homeproject.data.websocket

import com.example.homeproject.data.datamodels.Message
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.*

class WebSocketClient {
    private var messageListener: ((Int, String) -> Unit)? = null

    fun setListener(listener: (Int, String) -> Unit) {
        this.messageListener = listener
    }

    private val client = OkHttpClient()
    private val webSockets = mutableMapOf<Int, WebSocket>()
    private val url: String = "ws://10.0.2.2:8080"
    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            val idDialog = getIdDialog(webSocket)
            println("Connected to room $idDialog")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val idDialog = getIdDialog(webSocket)
            messageListener?.let { it(idDialog, text) }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            val idDialog = getIdDialog(webSocket)
            println("Connection failed for room $idDialog: ${t.message}")
            webSockets.remove(idDialog)
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            val idDialog = getIdDialog(webSocket)
            webSocket.close(code, reason)
            println("Connection closing for room $idDialog: $reason")
            webSockets.remove(idDialog)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            val idDialog = getIdDialog(webSocket)
            println("Connection closed for room $idDialog: $reason")
            webSockets.remove(idDialog)
        }
    }

    fun connectToDialog(idDialog: Int) {
        val request = Request.Builder()
            .url("$url/chat/$idDialog")
            .build()

        val webSocket = client.newWebSocket(request, listener)
        webSockets[idDialog] = webSocket
    }

    fun sendMessage(idDialog: Int, message: Message) {
        val messageJson = Json.encodeToString(message)
        webSockets[idDialog]?.send(messageJson)
    }

    fun closeConnection(idDialog: Int) {
        webSockets[idDialog]?.close(1000, "Closing connection")
    }

    fun closeAllConnections() {
        webSockets.forEach { (idDialog, webSocket) ->
            webSocket.close(1000, "Closing all connections")
        }
        webSockets.clear()
    }

    private fun getIdDialog(webSocket: WebSocket): Int {
        return webSockets.entries.firstOrNull { it.value == webSocket }?.key ?: -1
    }
}


