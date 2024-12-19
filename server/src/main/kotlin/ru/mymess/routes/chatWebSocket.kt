package ru.mymess.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.mymess.DataModels.Message
import ru.mymess.DataModels.ReceiveMessage
import ru.mymess.database.ConnectDB
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun Application.chatWebSocket() {
    val ipDialogs = mutableMapOf<String, MutableList<DefaultWebSocketSession>>()

    routing {
        webSocket("/chat/{idDialog}") {
            val idDialog = call.parameters["idDialog"] ?: return@webSocket close(
                CloseReason(
                    CloseReason.Codes.CANNOT_ACCEPT,
                    "No dialog ID"
                )
            )
            val dialog = ipDialogs.getOrPut(idDialog) { mutableListOf() }
            dialog.add(this)
            println(ipDialogs)
            try {
                // Обработка входящих сообщений
                incoming.consumeEach { frame ->
                    if (frame is Frame.Text) {
                        val text = frame.readText()

                        // Предположим, что входящее сообщение в формате JSON
                        val message = Json.decodeFromString<ReceiveMessage>(text)

                        val current = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
                        val time = current.format(formatter)

                        val messageToSave = ReceiveMessage(
                            idDialog = message.idDialog,
                            id_sender = message.id_sender,
                            message = message.message,
                            timeMessage = time
                        )

                        // Сохранение в базу данных (функция saveMessage)
                        saveMessage(idDialog.toInt(), messageToSave)

                        // Рассылка сообщения всем в комнате
                        val messageJson = Json.encodeToString(messageToSave)
                        dialog.forEach { session ->
                            session.send(Frame.Text(messageJson))
                            println("отправка сообщения: $messageJson")
                        }
                    }
                }
            } finally {
                // Удаление клиента из комнаты при отключении
                dialog.remove(this)
            }
        }
    }
}

fun saveMessage(idDialog: Int, message: ReceiveMessage) {
    val db = ConnectDB()
    db.insertMessage(message.idDialog, message.id_sender, message.message, message.timeMessage)
}