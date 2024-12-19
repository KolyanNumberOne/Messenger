package ru.mymess.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.mymess.DataModels.Message
import ru.mymess.database.ConnectDB
import ru.mymess.database.ListUsers

fun Application.postGetMessage() {
    routing {
        post("/getMessage") {

            val idDialog = call.receiveText().toInt()
            val db = ConnectDB()
            val messages = db.getMessagesByDialogId(idDialog)

            call.respond(messages)
        }
    }
}

